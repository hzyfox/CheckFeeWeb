

/**
 * create with checkfee
 * USER: husterfox
 */
public class UserInfo {
    private String location;
    private String building;
    private String roomNum;
    private String warning;
    private String email;

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAll(String location, String building, String roomNum, String warning, String email){
        setLocation(location);
        setBuilding(building);
        setRoomNum(roomNum);
        setWarning(warning);
        setEmail(email);
    }

    public boolean add(UserInfo userInfo){
        return UserInfoOp.add(userInfo);
    }

}
