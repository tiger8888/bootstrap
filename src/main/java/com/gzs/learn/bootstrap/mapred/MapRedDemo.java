package com.gzs.learn.bootstrap.mapred;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class MapRedDemo {
    public static class MapStage extends org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, IntWritable> {

    }

    public static class ReduceStage extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

    }

}
