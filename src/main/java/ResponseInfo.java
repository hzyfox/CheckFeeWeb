
import java.io.Serializable;

/**
 * create with PACKAGE_NAME
 * USER: husterfox
 */
public class ResponseInfo implements Serializable {
    private String elecValue;
    private boolean registerResult;

    public boolean isRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(boolean registerResult) {
        this.registerResult = registerResult;
    }

    public String getElecValue() {
        return elecValue;
    }

    public void setElecValue(String elecValue) {
        this.elecValue = elecValue;
    }
}
