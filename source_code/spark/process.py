from pyspark.sql.functions import *
from pyspark.sql.session import SparkSession
from pyspark.context import SparkContext

if __name__ == "__main__":
	sc = SparkContext("spark://spark-master:7077", "VDT2024")
	
	spark = SparkSession(sc)

	df_ds = spark.read.csv("hdfs://namenode:9000/danh_sach_sv_de.csv")

	df_me = df_ds.filter(col('_c0') == "5").select(col('_c0').cast("Integer").alias("student_code"), col('_c1').alias("student_name"))

	df_log_action_ds = spark.read.parquet("hdfs://namenode:9000/raw_zone/fact/activity/*")

	df_log_action_me = df_log_action_ds.filter(col('student_code') == 5).groupBy("student_code", "timestamp", "activity").agg(sum("numberOfFile").alias("totalFile"))

	df_result = df_me.join(df_log_action_me, ["student_code"], "inner")

	df_result_tmp = df_result.withColumn("timestamp", when(substring(col("timestamp"), 2, 1) == "/", concat(lit("0"), col("timestamp"))).otherwise(col("timestamp")))

	df_result_tmp2 = df_result_tmp.withColumn("timestamp", when(substring(col("timestamp"), 5, 1) == "/", concat(substring(col("timestamp"), 1, 3), lit("0"), substring(col("timestamp"), 4, 6))).otherwise(col("timestamp")))

	df_result_final = df_result_tmp2.withColumn("timestamp", concat(substring(col("timestamp"), 7, 4), substring(col("timestamp"), 1, 2), substring(col("timestamp"), 4, 2))).orderBy(col("timestamp"), col("activity")).select(col("timestamp").alias("date"), col("student_code"), col("student_name"), col("activity"), col("totalFile"))

	df_result_final.write.option("header", "true").option("delimiter", ",").mode("overwrite").csv("hdfs://namenode:9000/Tran_Ngoc_Bao")
