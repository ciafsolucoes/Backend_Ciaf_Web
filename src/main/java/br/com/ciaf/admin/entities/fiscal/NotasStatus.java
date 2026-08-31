package br.com.ciaf.admin.entities.fiscal;

public class NotasStatus {

    private String status;
    private String msgstatus;
    private String pathPdf;
    private String pathXml;
    private int	number;

    public NotasStatus() {}

    public NotasStatus(String status, String msgstatus, String pathPdf, String pathXml, int number) {
        super();
        this.status = status;
        this.msgstatus = msgstatus;
        this.pathPdf = pathPdf;
        this.pathXml = pathXml;
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsgstatus() {
        return msgstatus;
    }

    public void setMsgstatus(String msgstatus) {
        this.msgstatus = msgstatus;
    }

    public String getPathPdf() {
        return pathPdf;
    }

    public void setPathPdf(String pathPdf) {
        this.pathPdf = pathPdf;
    }

    public String getPathXml() {
        return pathXml;
    }

    public void setPathXml(String pathXml) {
        this.pathXml = pathXml;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
