package com.dream.city.base;

import lombok.Data;

/**
 * @param <T>
 * @author wvv
 * <p>
 * 操作结果
 */
@Data
public class Result<T> {

    /**
     * 操作是否成功
     */
    private  boolean success;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;


    public Result() {
    }


    public Result(String msg, int code) {
        super();
        this.msg = msg;
        this.code = code;
        if (code == Codes.OK.getCode()) {
            this.success = Boolean.TRUE;
        }
        this.data = null;
    }

    public Result(int code, T data) {
        super();
        this.msg = null;
        this.code = code;
        this.data = data;
        if (code == Codes.OK.getCode()) {
            this.success = Boolean.TRUE;
        }
    }

    public Result(boolean success, String msg, T data) {
        super();
        this.success = success;
        this.msg = msg;
        this.data = data;
        if (success) {
            this.code = Codes.OK.getCode();
        }
    }

    public Result(boolean success, String msg) {
        super();
        this.success = success;
        if (success) {
            this.code = Codes.OK.getCode();
        }
        this.msg = msg;
        this.data = null;
    }

    public Result(boolean success, T data) {
        super();
        this.success = success;
        this.msg = null;
        this.data = data;
        if (success) {
            this.code = Codes.OK.getCode();
        }
    }

    public Result(String msg, int code, T data) {
        super();
        this.msg = msg;
        this.code = code;
        this.data = data;
        if (code == Codes.OK.getCode()) {
            this.success = Boolean.TRUE;
        }
    }

    public Result(boolean success, String msg, int code, T data) {
        super();
        this.success = success;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Result(boolean success){
        super();
        this.success = success;
        this.msg = null;
        this.data = null;
        if (success) {
            this.code = Codes.OK.getCode();
        }
    }

    public boolean getSuccess() {
        return success;
    }

    public static Result result(boolean success, String msg, int code, Object data) {
        return new Result(success, msg, code, data);
    }

    public static Result result(boolean success, String msg, int code) {
        return new Result(success, msg, code);
    }

    public static Result result(boolean success, String msg) {
        return new Result(success, msg);
    }

    public static Result result(boolean success, int code) {
        return new Result(success, code);
    }

    public static Result result(String msg, int code, Object data) {
        return new Result(msg, code, data);
    }

    public static Result result(boolean success) {
        return new Result(success);
    }


    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /*public static void main(String[] args) {
        Result result = Result.result(true,"tesst",200);
        if (result.getSuccess()){
            System.out.println("true");
        }
        assert result.getSuccess();
    }*/
}



