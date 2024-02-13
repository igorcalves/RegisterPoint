package br.com.igor.registerPoint.RegisterPoint.domain;

public enum Department {

    
OFFICE("office"),
DEVELOPMENT_AREA("dev area"),
RECEPTION ("reception");

    String dep;

    Department(String dep){
        this.dep = dep;
    }

    public String getDepartment(){
        return dep;
    }
}
