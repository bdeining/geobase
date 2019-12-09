package com.bdeining.hbase.rest;

import com.blade.Blade;

public class RestEndpoint {

  public static void main(String[] args) {
    Blade.of().get("/", ctx -> ctx.text("Hello Blade")).start();
    System.out.println("hi");
  }
}
