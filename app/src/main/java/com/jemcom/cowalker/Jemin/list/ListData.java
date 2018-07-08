package com.jemcom.cowalker.Jemin.list;

public class ListData{


    public int project_bgUrl;
    private String project_title, project_purpose, project_field, project_location;

    public ListData(String project_title, String project_purpose, String project_field, String project_location, int project_bgUrl){
        this.project_title = project_title;
        this.project_purpose = project_purpose;
        this.project_field = project_field;
        this.project_location = project_location;
        this.project_bgUrl = project_bgUrl;
    }

    public String getTitle(){
        return project_title;
    }

    public String getPurpose(){
        return project_purpose;
    }

    public String getField(){
        return project_field;
    }


    public String getLocation(){
        return project_location;
    }

    public int getProjectBgUrl(){
        return project_bgUrl;
    }


}
