# Assignment - Data Engineering - Viettel Digital Talent 2024

## Project introduction
<ul>
  <li>Project objective: Build a simple data platform using some of the Apache's technologies (Kafka, Nifi, Hadoop, Spark) trained in Data Engineering - Viettel Digital Talent 2024</li>
  <li>Detail:
    <ul>
      <li>Write a program to push data to Kafka Topic</li>
      <li>Deploy Nifi, pull data from Kafka Topic “vdt2024”, process and save data to HDFS, save data as parquet</li>
      <li>Store the file “danh_sach_sv_de.csv” to HDFS</li>
      <li>Write a program to process data stored under HDFS, using Apache Spark</li>
    </ul>
  </li>
  
### System model
  <img src="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/illustration/flow.png">
</ul>


## Deploy system
<ul>
  <li>You should download images before</li>
  
```sh
docker pull { confluentinc/cp-zookeeper, confluentinc/cp-kafka, provectuslabs/kafka-ui, apache/nifi, bde2020/hadoop-namenode:2.0.0-hadoop3.2.1-java8, bitnami/spark }
```

  <li>Move to clone project and Start system</li>
  
```sh
docker compose up -d
```

  <li>After start system, all port of containers in local website:
    <ul>
      <li>Kafka: 8282</li>
      <li>Nifi: 8181</li>
      <li>HDFS: 9870</li>
      <li>Spark: 8080</li>
    </ul>
  </li>
  <li>Run project Java in <a href="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/tree/master/source_code/kafka">source_code/kafka</a> to publish data to Kafka Topic "vdt2024"</li>
  <li>Start all processers in Nifi to put data to HDFS and put <a href="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/source_code/input/danh_sach_sv_de.csv">file</a> from local to HDFS</li>
  <li>Move to spark-master container</li>

```sh
docker exec -it spark-master bash
```

  <li>Move code pyspark from local to spark-master container</li>

```sh
docker cp soucre_code/spark/process.py spark-master:/bin
```

  <li>Run code pyspark in spark-master</li>

```sh
bin/spark-submit process.py
```

</ul>

## Demo
### Kafka
  <img style="width:75%" src="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/illustration/kafka_ui.png">
  
### Nifi
  <img style="width:75%" src="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/illustration/nifi.png">
  
### HDFS
  <img style="width:75%" src="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/illustration/local_to_hdfs.png">
  
### Spark
  <img style="width:75%" src="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/illustration/spark.png">

### Output
  <img style="width:75%" src="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/blob/master/illustration/output.png">


## Report
<ul>
  <li><a href="https://github.com/Tran-Ngoc-Bao/IntroductionToDataEngineering/tree/master/report/report.pdf">Report</a></li>
</ul>
