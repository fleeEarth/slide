package my.verify.slide.utils;

public class Result {

    int code;
    Object data;
    String message;

    Result(int code,String message){
        this.code = code;
        this.message = message;
    }

    Result(int code,Object data,String message){
        this.code = code;
        this.data = data;
        this.message = message;
    }


    public static Result success(Object data){
        return new Result(200,data,"success");
    }

    public static Result error(Object data){
        return new Result(200,data,"error");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
