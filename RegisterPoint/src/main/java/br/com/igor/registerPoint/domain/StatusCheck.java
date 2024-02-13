package br.com.igor.registerPoint.domain;

public enum StatusCheck {

    IN("in"),
    OUT("out");
  
    
    String check;

    StatusCheck(String check){
        this.check = check;
    }

    public String getCheck(){
        return check;
    }
}
