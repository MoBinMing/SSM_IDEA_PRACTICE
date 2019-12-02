package info.lzzy.models;

import java.util.Date;

public class Api {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.id
     *
     * @mbg.generated
     */
    private String id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.role
     *
     * @mbg.generated
     */
    private String role;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.info
     *
     * @mbg.generated
     */
    private String info;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.url
     *
     * @mbg.generated
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.type
     *
     * @mbg.generated
     */
    private String type;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.state
     *
     * @mbg.generated
     */
    private Integer state;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.succeed_html
     *
     * @mbg.generated
     */
    private String succeedHtml;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.defeated_html
     *
     * @mbg.generated
     */
    private String defeatedHtml;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.add_time
     *
     * @mbg.generated
     */
    private Date addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column api.request_data
     *
     * @mbg.generated
     */
    private String requestData;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api
     *
     * @mbg.generated
     */
    public Api(String id, String role, String name, String info, String url, String type, Integer state, String succeedHtml, String defeatedHtml, Date addTime, String requestData) {
        this.id = id;
        this.role = role;
        this.name = name;
        this.info = info;
        this.url = url;
        this.type = type;
        this.state = state;
        this.succeedHtml = succeedHtml;
        this.defeatedHtml = defeatedHtml;
        this.addTime = addTime;
        this.requestData = requestData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table api
     *
     * @mbg.generated
     */
    public Api() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.id
     *
     * @return the value of api.id
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.id
     *
     * @param id the value for api.id
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.role
     *
     * @return the value of api.role
     *
     * @mbg.generated
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.role
     *
     * @param role the value for api.role
     *
     * @mbg.generated
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.name
     *
     * @return the value of api.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.name
     *
     * @param name the value for api.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.info
     *
     * @return the value of api.info
     *
     * @mbg.generated
     */
    public String getInfo() {
        return info;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.info
     *
     * @param info the value for api.info
     *
     * @mbg.generated
     */
    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.url
     *
     * @return the value of api.url
     *
     * @mbg.generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.url
     *
     * @param url the value for api.url
     *
     * @mbg.generated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.type
     *
     * @return the value of api.type
     *
     * @mbg.generated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.type
     *
     * @param type the value for api.type
     *
     * @mbg.generated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.state
     *
     * @return the value of api.state
     *
     * @mbg.generated
     */
    public Integer getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.state
     *
     * @param state the value for api.state
     *
     * @mbg.generated
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.succeed_html
     *
     * @return the value of api.succeed_html
     *
     * @mbg.generated
     */
    public String getSucceedHtml() {
        return succeedHtml;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.succeed_html
     *
     * @param succeedHtml the value for api.succeed_html
     *
     * @mbg.generated
     */
    public void setSucceedHtml(String succeedHtml) {
        this.succeedHtml = succeedHtml == null ? null : succeedHtml.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.defeated_html
     *
     * @return the value of api.defeated_html
     *
     * @mbg.generated
     */
    public String getDefeatedHtml() {
        return defeatedHtml;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.defeated_html
     *
     * @param defeatedHtml the value for api.defeated_html
     *
     * @mbg.generated
     */
    public void setDefeatedHtml(String defeatedHtml) {
        this.defeatedHtml = defeatedHtml == null ? null : defeatedHtml.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.add_time
     *
     * @return the value of api.add_time
     *
     * @mbg.generated
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.add_time
     *
     * @param addTime the value for api.add_time
     *
     * @mbg.generated
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column api.request_data
     *
     * @return the value of api.request_data
     *
     * @mbg.generated
     */
    public String getRequestData() {
        return requestData;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column api.request_data
     *
     * @param requestData the value for api.request_data
     *
     * @mbg.generated
     */
    public void setRequestData(String requestData) {
        this.requestData = requestData == null ? null : requestData.trim();
    }
}