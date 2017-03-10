package com.knoldus.factories

import com.knoldus.domain.User
import com.knoldus.domain.UuidPtp1588TimingPacket
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import com.datastax.spark.connector._

import scala.util.Try

/**
  * Created by shivansh on 9/5/16.
  */
trait DatabaseAccess {

  import Context._

  def create(user: User): Boolean =
    Try(sc.parallelize(Seq(user)).saveToCassandra(keyspace, tableName)).toOption.isDefined

  def retrieve(id: String): Option[Array[User]] = Try(
    sc.cassandraTable[User](keyspace, tableName)
      .where(s"id='$id'")
      .collect()
  ).toOption

  def retrievePTP(experimentId: String, macAddress: String, timeBucket: String): Option[Array[UuidPtp1588TimingPacket]] = Try(
    sc.cassandraTable[UuidPtp1588TimingPacket](keyspace, tableName)
      .where(s"experiment_id=$experimentId")
      .where(s"mac_address=$macAddress")
      .where(s"time_bucket='$timeBucket'")
      .collect()
  ).toOption
}

object DatabaseAccess extends DatabaseAccess


object Context {
  //  val masterAddress = "spark://192.168.1.72:7077"
  val masterAddress = "local[4]"
  val config = ConfigFactory.load()
  val url = config.getString("cassandra.url")
  val sparkConf: SparkConf = new SparkConf().setAppName("cassandra-api").setMaster(masterAddress)
    .set("spark.cassandra.connection.host", url)
  val spark = SparkSession.builder().config(sparkConf).getOrCreate()
  val sc = spark.sparkContext
  val keyspace = config.getString("cassandra.keyspace")
  val tableName = config.getString("cassandra.tableName")
}