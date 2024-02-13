package br.com.igor.registerPoint.RegisterPoint.domain;

public enum PositionJob {

    SOFTWARE_DEVELOPER("dev"),
    MANAGER("mgr"),
    INTERN("intern");

    String position;

    PositionJob(String position){
        this.position = position;
    }

    public String getPosition(){
        return position;
    }
    
}
