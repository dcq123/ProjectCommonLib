package cn.qing.soft.networklib;

/**
 * 所有Model的基类，定义了通用的状态和消息
 */
public abstract class BaseModel {

    public String Message;
    public String Status;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
