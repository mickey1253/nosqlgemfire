package io.pivotal;

/**
 * @author rajkannan
 * @since 3/26/17.
 */
public class CustomerO {

  private Long id;
  private String tsp;
  private String name;
  private String username;
  private String password;
  private String dbname;
  private String updated_by;
  private Long updated_on;
  private Integer active_status_id;
  private Integer app_status_id;
  private String customer_name;
  private Integer customer_number;
  private String entered_by;
  private String entered_on;
  private String active_status_description;
  private String app_status_description;

  public CustomerO(Long id, String tsp, String name, String username, String password, String dbname, String updated_by, Long updated_on, Integer active_status_id, Integer app_status_id, String customer_name, Integer customer_number, String entered_by, String entered_on, String active_status_description, String app_status_description) {
    this.id = id;
    this.tsp = tsp;
    this.name = name;
    this.username = username;
    this.password = password;
    this.dbname = dbname;
    this.updated_by = updated_by;
    this.updated_on = updated_on;
    this.active_status_id = active_status_id;
    this.app_status_id = app_status_id;
    this.customer_name = customer_name;
    this.customer_number = customer_number;
    this.entered_by = entered_by;
    this.entered_on = entered_on;
    this.active_status_description = active_status_description;
    this.app_status_description = app_status_description;
  }

  public CustomerO(){}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTsp() {
    return tsp;
  }

  public void setTsp(String tsp) {
    this.tsp = tsp;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getDbname() {
    return dbname;
  }

  public void setDbname(String dbname) {
    this.dbname = dbname;
  }

  public String getUpdated_by() {
    return updated_by;
  }

  public void setUpdated_by(String updated_by) {
    this.updated_by = updated_by;
  }

  public Long getUpdated_on() {
    return updated_on;
  }

  public void setUpdated_on(Long updated_on) {
    this.updated_on = updated_on;
  }

  public Integer getActive_status_id() {
    return active_status_id;
  }

  public void setActive_status_id(Integer active_status_id) {
    this.active_status_id = active_status_id;
  }

  public Integer getApp_status_id() {
    return app_status_id;
  }

  public void setApp_status_id(Integer app_status_id) {
    this.app_status_id = app_status_id;
  }

  public String getCustomer_name() {
    return customer_name;
  }

  public void setCustomer_name(String customer_name) {
    this.customer_name = customer_name;
  }

  public Integer getCustomer_number() {
    return customer_number;
  }

  public void setCustomer_number(Integer customer_number) {
    this.customer_number = customer_number;
  }

  public String getEntered_by() {
    return entered_by;
  }

  public void setEntered_by(String entered_by) {
    this.entered_by = entered_by;
  }

  public String getEntered_on() {
    return entered_on;
  }

  public void setEntered_on(String entered_on) {
    this.entered_on = entered_on;
  }

  public String getActive_status_description() {
    return active_status_description;
  }

  public void setActive_status_description(String active_status_description) {
    this.active_status_description = active_status_description;
  }

  public String getApp_status_description() {
    return app_status_description;
  }

  public void setApp_status_description(String app_status_description) {
    this.app_status_description = app_status_description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CustomerO customer = (CustomerO) o;

    if (!tsp.equals(customer.tsp)) {
      return false;
    }
    return name.equals(customer.name);

  }

  @Override
  public int hashCode() {
    int result = tsp.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }



  @Override
  public String toString() {
    return "CustomerO{" +
            "id=" + id +
            ", tsp='" + tsp + '\'' +
            ", name='" + name + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", dbname='" + dbname + '\'' +
            ", updated_by='" + updated_by + '\'' +
            ", updated_on=" + updated_on +
            ", active_status_id=" + active_status_id +
            ", app_status_id=" + app_status_id +
            ", customer_name='" + customer_name + '\'' +
            ", customer_number=" + customer_number +
            ", entered_by='" + entered_by + '\'' +
            ", entered_on='" + entered_on + '\'' +
            ", active_status_description='" + active_status_description + '\'' +
            ", app_status_description='" + app_status_description + '\'' +
            '}';
  }

}
