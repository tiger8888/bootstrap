package com.gzs.learn.bootstrap.mapred;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class MapRedDemo {
    public static class MapStage extends Mapper<Object, Text, Text, IntWritable> {

    }

    public static class ReduceStage extends Reducer<Text, IntWritable, Text, IntWritable> {

    }

}
