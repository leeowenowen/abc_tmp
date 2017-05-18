package com.owo.common.model;

public class Result<T> {
  private int code;
  private String msg;
  private T data;

  public int code() {
    return code;
  }

  public Result<T> code(int code) {
    this.code = code;
    return this;
  }

  public String msg() {
    return msg;
  }

  public Result<T> msg(String msg) {
    this.msg = msg;
    return this;
  }

  public T data() {
    return data;
  }

  public Result<T> data(T data) {
    this.data = data;
    return this;
  }

  public boolean success() {
    return code == ResultCode.SUCCESS;
  }

  public static <P> Result<P> make(int code, String msg, P data) {
    Result<P> result = new Result<>();
    result.code(code).msg(msg).data(data);
    return result;
  }
}
