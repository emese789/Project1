package com.example.planningpokeradmin;

public class Fire_Form {
    private  String NAME;
    private  String GROUPNAME;

    public Fire_Form(){

    }

    public Fire_Form( String NAME,String GROUPNAME){
        //this.ID = ID;
        this.NAME = NAME;
        this.GROUPNAME = GROUPNAME;
    }

    public String getNAME() {
        return NAME;
    }

    public String getGROUPNAME(){
        return GROUPNAME;
    }

}
