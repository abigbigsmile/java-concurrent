package com.smile.thread.common;

public class MessageResponse<T> {

    private int status;
    private String message;
    private T data;

    private MessageResponse(int status) {
        this.status = status;
    }

    private MessageResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private MessageResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private MessageResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess(){
        return this.status == StatusCode.SUCCESS.getCode();
    }

    public static <T> MessageResponse<T> successStatus(){
        return new MessageResponse<>(StatusCode.SUCCESS.getCode());
    }

    public static <T> MessageResponse<T> successMessage(String message){
        return new MessageResponse<T>(StatusCode.SUCCESS.getCode(), message);
    }

    public static <T> MessageResponse<T> successData(T data){
        return new MessageResponse<T>(StatusCode.SUCCESS.getCode(), data);
    }

    public static <T> MessageResponse<T> successMessageData(String message, T data){
        return new MessageResponse<T>(StatusCode.SUCCESS.getCode(), message, data);
    }

    public static <T> MessageResponse<T> errorStatus(){
        return new MessageResponse<T>(StatusCode.ERROR.getCode());
    }

    public static <T> MessageResponse<T> errorMessage(String message){
        return new MessageResponse<T>(StatusCode.ERROR.getCode(),message);
    }
}
