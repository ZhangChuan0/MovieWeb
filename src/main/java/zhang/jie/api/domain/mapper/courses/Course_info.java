package zhang.jie.api.domain.mapper.courses;


import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Map;

/*
* 根据course_info表生成对应的DTO
* */
public class Course_info implements Serializable {

        private Integer id;
        private String courseName;
        private String brief;
        private String teacherName;
        private String teacherPosition;
        private String teacherDescription;
        private String previewFirstField;
        private String previewSecondField;
        private String sortNum;
        private String courseListImg;
        private String courseImgUrl;
        private Double discounts;
        private Double price;
        private Integer sales;
        private String discountTag;
        private String activityCourse;
        private String activityBeginTime;
        private String activityEndTime;
        private Double activityAmount;
        private Integer activityStock;
        private Integer status;
        private String courseDescriptionMarkDown;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherPosition() {
            return teacherPosition;
        }

        public void setTeacherPosition(String teacherPosition) {
            this.teacherPosition = teacherPosition;
        }

        public String getTeacherDescription() {
            return teacherDescription;
        }

        public void setTeacherDescription(String teacherDescription) {
            this.teacherDescription = teacherDescription;
        }

        public String getPreviewFirstField() {
            return previewFirstField;
        }

        public void setPreviewFirstField(String previewFirstField) {
            this.previewFirstField = previewFirstField;
        }

        public String getPreviewSecondField() {
            return previewSecondField;
        }

        public void setPreviewSecondField(String previewSecondField) {
            this.previewSecondField = previewSecondField;
        }

        public String getSortNum() {
            return sortNum;
        }

        public void setSortNum(String sortNum) {
            this.sortNum = sortNum;
        }

        public String getCourseListImg() {
            return courseListImg;
        }

        public void setCourseListImg(String courseListImg) {
            this.courseListImg = courseListImg;
        }

        public String getCourseImgUrl() {
            return courseImgUrl;
        }

        public void setCourseImgUrl(String courseImgUrl) {
            this.courseImgUrl = courseImgUrl;
        }

        public Double getDiscounts() {
            return discounts;
        }

        public void setDiscounts(Double discounts) {
            this.discounts = discounts;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Integer getSales() {
            return sales;
        }

        public void setSales(Integer sales) {
            this.sales = sales;
        }

        public String getDiscountTag() {
            return discountTag;
        }

        public void setDiscountTag(String discountTag) {
            this.discountTag = discountTag;
        }

        public String getActivityCourse() {
            return activityCourse;
        }

        public void setActivityCourse(String activityCourse) {
            this.activityCourse = activityCourse;
        }

        public String getActivityBeginTime() {
            return activityBeginTime;
        }

        public void setActivityBeginTime(String activityBeginTime) {
            this.activityBeginTime = activityBeginTime;
        }

        public String getActivityEndTime() {
            return activityEndTime;
        }

        public void setActivityEndTime(String activityEndTime) {
            this.activityEndTime = activityEndTime;
        }

        public Double getActivityAmount() {
            return activityAmount;
        }

        public void setActivityAmount(Double activityAmount) {
            this.activityAmount = activityAmount;
        }

        public Integer getActivityStock() {
            return activityStock;
        }

        public void setActivityStock(Integer activityStock) {
            this.activityStock = activityStock;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCourseDescriptionMarkDown() {
            return courseDescriptionMarkDown;
        }

        public void setCourseDescriptionMarkDown(String courseDescriptionMarkDown) {
            this.courseDescriptionMarkDown = courseDescriptionMarkDown;
        }

        private Map<String ,Object> map;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public void setDiscounts(Object value) {
    }
}



