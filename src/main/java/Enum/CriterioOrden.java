package Enum;

public enum CriterioOrden {

    ADDRESS_LINE("address_line"),
    AGENCY_CODE("agency_code"),
    DISTANCE("distance");

    private String criteroOrden;

    CriterioOrden(String criteroOrden) {
        this.criteroOrden = criteroOrden;
    }

    public String getCriteroOrden() {
        return criteroOrden;
    }

    public void setCriteroOrden(String criteroOrden) {
        this.criteroOrden = criteroOrden;
    }
}
