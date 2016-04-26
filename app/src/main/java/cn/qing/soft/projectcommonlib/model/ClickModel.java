package cn.qing.soft.projectcommonlib.model;

/**
 * 简单的列表点击跳转Model
 */
public class ClickModel {

    private String title;
    private Class activityClass;
    private String className;

    public ClickModel(Class activityClass, String title, String className) {
        this.activityClass = activityClass;
        this.title = title;
        this.className = className;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
