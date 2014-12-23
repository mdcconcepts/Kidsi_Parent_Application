package org.mdcconcepts.kidsi.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * This class contains all data saved in shared preferences
 *
 * @author Admin
 */
public class AppSharedPreferences {

    private SharedPreferences prefs;
    private Editor editor;


    boolean isFirstRun;
    public AppSharedPreferences(Context context) {
        prefs = getMyPreferences(context);
        editor = prefs.edit();
    }

    private static SharedPreferences getMyPreferences(Context context) {
        return context.getSharedPreferences(Util.APP_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    public void clearAllSharedPreferences() {
        editor.clear();
        editor.commit();
    }

    public boolean isFirstRun() {
        return prefs.getBoolean("isFirstRun", true);
    }

    public void setFirstRun(Boolean isFirstRun) {
        editor = prefs.edit();
        editor.putBoolean("isFirstRun", isFirstRun);
        editor.commit();
    }

    /**
     * Set Username
     *
     * @param value
     */
    public void setUname(String value) {
        editor.putString("Uname", value);
        editor.commit();
    }

    /**
     * Get Username
     *
     * @return
     */
    public String getUname() {
        return prefs.getString("Uname", "");
    }

    /**
     * Set Password
     *
     * @param value
     */
    public void setPassword(String value) {
        editor = prefs.edit();
        editor.putString("Password", value);
        editor.commit();
    }

    /**
     * Get Password
     *
     * @return
     */
    public String getPassword() {
        return prefs.getString("Password", "");
    }


    /**
     * Set Parent_Id
     *
     * @param value
     */
    public void setParentId(String value) {
        editor = prefs.edit();
        editor.putString("Parent_Id", value);
        editor.commit();
    }

    /**
     * Get Parent_Id
     *
     * @return
     */
    public String getParentId() {
        return prefs.getString("Parent_Id", "-1");
    }

    /**
     * Set School_Id
     *
     * @param value
     */
    public void setSchoolId(String value) {
        editor = prefs.edit();
        editor.putString("School_Id", value);
        editor.commit();
    }

    /**
     * Get School_Id
     *
     * @return
     */
    public String getSchoolId() {
        return prefs.getString("School_Id", "-1");
    }

    /**
     * Set School_Id
     *
     * @param value
     */
    public void setKidId(String value) {
        editor = prefs.edit();
        editor.putString("Kid_Id", value);
        editor.commit();
    }

    /**
     * Get School_Id
     *
     * @return
     */
    public String getKidId() {
        return prefs.getString("Kid_Id", "-1");
    }


    /**
     * * Performance Rating Info**
     */

    public int getDrawRating() {
        return prefs.getInt("DrawRating", 0);
    }

    public void setDrawRating(int drawRating) {
        editor = prefs.edit();
        editor.putInt("DrawRating", drawRating);
        editor.commit();
    }

    public int getPlayRating() {
        return prefs.getInt("PlayRating", 0);
    }

    public void setPlayRating(int playRating) {
        editor = prefs.edit();
        editor.putInt("PlayRating", playRating);
        editor.commit();
    }


    public int getSingRating() {
        return prefs.getInt("SingRating", 0);
    }

    public void setSingRating(int singRating) {
        editor.putInt("SingRating", singRating);
        editor.commit();
    }

    /**
     * Health Report Ratings*
     */

    public int getSleepRating() {
        return prefs.getInt("SleepRating", 0);
    }

    public void setSleepRating(int sleepRating) {
        editor.putInt("SleepRating", sleepRating);
        editor.commit();
    }

    public int getFoodRating() {
        return prefs.getInt("FoodRating", 0);
    }

    public void setFoodRating(int foodRating) {
        editor.putInt("FoodRating", foodRating);
        editor.commit();
    }

    public int getMilkRating() {
        return prefs.getInt("MilkRating", 0);
    }

    public void setMilkRating(int milkRating) {
        editor.putInt("MilkRating", milkRating);
        editor.commit();
    }


    /**
     * Set School_Address
     *
     * @param value
     */
    public void setSchoolAddress(String value) {
        editor = prefs.edit();
        editor.putString("School_Address", value);
        editor.commit();
    }
    /*** School Info***/
    /**
     * Get School_Address
     *
     * @return
     */
    public String getSchoolAddress() {
        return prefs.getString("School_Address", "");
    }

    /**
     * Set School_Website
     *
     * @param value
     */
    public void setSchoolWebsite(String value) {
        editor = prefs.edit();
        editor.putString("School_Website", value);
        editor.commit();
    }

    /**
     * Get School_Website
     *
     * @return
     */
    public String getSchoolWebsite() {
        return prefs.getString("School_Website", "");
    }

    /**
     * Set School_MobileNumber
     *
     * @param value
     */
    public void setSchoolMobileNumber(String value) {
        editor = prefs.edit();
        editor.putString("School_MobileNumber", value);
        editor.commit();
    }

    /**
     * Get School_MobileNumber
     */
    public String getSchoolMobileNumber() {
        return prefs.getString("School_MobileNumber", "");
    }

    /**
     * Set School_FB_URL
     *
     * @param value
     */
    public void setSchoolFBURL(String value) {

        editor = prefs.edit();
        editor.putString("School_FB_URL", value);
        editor.commit();
    }

    /**
     * Get School_FB_URL
     *
     * @return
     */
    public String getSchoolFBURL() {

        return prefs.getString("School_FB_URL", "");

    }

    /**
     * Set School_Twitter_URL
     *
     * @param value
     */
    public void setSchoolTwitterURL(String value) {

        editor = prefs.edit();
        editor.putString("School_Twitter_URL", value);
        editor.commit();
    }

    /**
     * Get School_FB_URL
     *
     * @return
     */
    public String getSchoolTwitterURL() {
        return prefs.getString("School_Twitter_URL", "");
    }

    /**
     * Set School_LinkedIn_URL
     *
     * @param value
     */
    public void setSchoolLinkedInURL(String value) {

        editor = prefs.edit();
        editor.putString("School_LinkedIn_URL", value);
        editor.commit();
    }

    /**
     * Get School_LinkedIn_URL
     *
     * @return
     */
    public String getSchoolLinkedInURL() {
        return prefs.getString("School_LinkedIn_URL", "");
    }

    public String getSchoolLandlineNumber() {
        return prefs.getString("SchoolLandlineNumber", "");
    }

    public void setSchoolLandlineNumber(String schoolLandlineNumber) {
        editor = prefs.edit();
        editor.putString("SchoolLandlineNumber", schoolLandlineNumber);
        editor.commit();
    }

    public String getSchoolEmailId() {
        return prefs.getString("SchoolEmailId", "");
    }

    public void setSchoolEmailId(String schoolEmailId) {
        editor = prefs.edit();
        editor.putString("SchoolEmailId", schoolEmailId);
        editor.commit();
    }


    public String getSchoolGPlusURL() {
        return prefs.getString("SchoolGPlusURL", "");
    }

    public void setSchoolGPlusURL(String schoolGPlusURL) {
        editor = prefs.edit();
        editor.putString("SchoolGPlusURL", schoolGPlusURL);
        editor.commit();
    }

    public Boolean getSchoolInfoStatus() {
        return prefs.getBoolean("SchoolInfoStatus", false);
    }

    public void setSchoolInfoStatus(Boolean schoolInfoStatus) {
        editor = prefs.edit();
        editor.putBoolean("SchoolInfoStatus", schoolInfoStatus);
        editor.commit();
    }

    /**
     * Teacher Info**
     */
    public String getTeacherName() {
        return prefs.getString("TeacherName", "");
    }

    public void setTeacherName(String teacherName) {
        editor = prefs.edit();
        editor.putString("TeacherName", teacherName);
        editor.commit();
    }

    public String getTeacherStatus() {
        return prefs.getString("TeacherStatus", "");
    }

    public void setTeacherStatus(String teacherStatus) {
        editor = prefs.edit();
        editor.putString("TeacherStatus", teacherStatus);
        editor.commit();
    }

    public int getTeacherRating() {
        return prefs.getInt("TeacherRating", 0);
    }

    public void setTeacherRating(int teacherRating) {
        editor = prefs.edit();
        editor.putInt("TeacherRating", teacherRating);
        editor.commit();
    }

    public String getTeacherMobileNumber() {
        return prefs.getString("TeacherMobileNumber", "");
    }

    public void setTeacherMobileNumber(String teacherMobileNumber) {
        editor = prefs.edit();
        editor.putString("TeacherMobileNumber", teacherMobileNumber);
        editor.commit();
    }

    public String getTeacherEmergancyNumber() {
        return prefs.getString("TeacherEmergancyNumber", "");
    }

    public void setTeacherEmergancyNumber(String teacherEmergancyNumber) {
        editor = prefs.edit();
        editor.putString("TeacherEmergancyNumber", teacherEmergancyNumber);
        editor.commit();
    }

    public String getTeacherClassName() {
        return prefs.getString("TeacherClassName", "");
    }

    public void setTeacherClassName(String teacherClassName) {
        editor = prefs.edit();
        editor.putString("TeacherClassName", teacherClassName);
        editor.commit();
    }

    public String getTeacherEmailAddress() {
        return prefs.getString("TeacherEmailAddress", null);
    }

    public void setTeacherEmailAddress(String teacherEmailAddress) {
        editor = prefs.edit();
        editor.putString("TeacherEmailAddress", teacherEmailAddress);
        editor.commit();
    }


    public Boolean getTeacherInfoStatus() {
        return prefs.getBoolean("TeacherInfoStatus", false);
    }

    public void setTeacherInfoStatus(Boolean teacherInfoStatus) {
        editor = prefs.edit();
        editor.putBoolean("TeacherInfoStatus", teacherInfoStatus);
        editor.commit();
    }


    public String getParentName() {
        return prefs.getString("ParentName", null);
    }

    public void setParentName(String parentName) {
        editor = prefs.edit();
        editor.putString("ParentName", parentName);
        editor.commit();
    }

    public String getParentPrimaryMobileNumber() {
        return prefs.getString("ParentPrimaryMobileNumber", null);
    }

    public void setParentPrimaryMobileNumber(String parentPrimaryMobileNumber) {
        editor = prefs.edit();
        editor.putString("ParentPrimaryMobileNumber", parentPrimaryMobileNumber);
        editor.commit();
    }

    public String getParentEmergancyMobileNumber() {
        return prefs.getString("ParentEmergancyMobileNumber", null);
    }

    public void setParentEmergancyMobileNumber(String parentEmergancyMobileNumber) {
        editor = prefs.edit();
        editor.putString("ParentEmergancyMobileNumber", parentEmergancyMobileNumber);
        editor.commit();
    }

    public String getParentCompanyMobileNumber() {
        return prefs.getString("ParentCompanyMobileNumber", null);
    }

    public void setParentCompanyMobileNumber(String parentCompanyMobileNumber) {
        editor = prefs.edit();
        editor.putString("ParentCompanyMobileNumber", parentCompanyMobileNumber);
        editor.commit();
    }

    public String getParentEmailAddr() {
        return prefs.getString("ParentEmailAddr", null);
    }

    public void setParentEmailAddr(String parentEmailAddr) {
        editor = prefs.edit();
        editor.putString("ParentEmailAddr", parentEmailAddr);
        editor.commit();
    }

    public String getParentAddress() {
        return prefs.getString("ParentAddress", null);
    }

    public void setParentAddress(String parentAddress) {
        editor = prefs.edit();
        editor.putString("ParentAddress", parentAddress);
        editor.commit();
    }

    public Boolean getParentInfoStatus() {
        return prefs.getBoolean("ParentInfoStatus", false);
    }

    public void setParentInfoStatus(Boolean parentInfoStatus) {
        editor = prefs.edit();
        editor.putBoolean("ParentInfoStatus", parentInfoStatus);
        editor.commit();
    }

    public String getParentProfileUrl() {
        return prefs.getString("parentProfileUrl", null);
    }

    public void setParentProfileUrl(String parentProfileUrl) {
        editor = prefs.edit();
        editor.putString("parentProfileUrl", parentProfileUrl);
        editor.commit();
    }

    String parentProfileUrl;
    public Boolean getKidInfoStatus() {
        return prefs.getBoolean("KidInfoStatus", false);
    }

    public void setKidInfoStatus(Boolean kidInfoStatus) {
        editor = prefs.edit();
        editor.putBoolean("KidInfoStatus", kidInfoStatus);
        editor.commit();
    }

    public String getKidName() {
        return prefs.getString("KidName", null);
    }

    public void setKidName(String kidName) {
        editor = prefs.edit();
        editor.putString("KidName", kidName);
        editor.commit();
    }

    public String getKidWeight() {
        return prefs.getString("KidWeight", null);
    }

    public void setKidWeight(String kidWeight) {
        editor = prefs.edit();
        editor.putString("KidWeight", kidWeight);
        editor.commit();
    }

    public String getKidAge() {
        return prefs.getString("KidAge", null);
    }

    public void setKidAge(String kidAge) {
        editor = prefs.edit();
        editor.putString("KidAge", kidAge);
        editor.commit();
    }

    public String getKidBloodGroup() {
        return prefs.getString("KidBloodGroup", null);
    }

    public void setKidBloodGroup(String kidBloodGroup) {
        editor = prefs.edit();
        editor.putString("KidBloodGroup", kidBloodGroup);
        editor.commit();
    }

    public String getKidGaurdian() {
        return prefs.getString("KidGaurdian", null);
    }

    public void setKidGaurdian(String kidGaurdian) {
        editor = prefs.edit();
        editor.putString("KidGaurdian", kidGaurdian);
        editor.commit();
    }

    public String getKidClass() {
        return prefs.getString("KidClass", null);
    }

    public void setKidClass(String kidClass) {
        editor = prefs.edit();
        editor.putString("KidClass", kidClass);
        editor.commit();
    }

    public String getKidGender() {
        return prefs.getString("KidGender", null);
    }

    public void setKidGender(String kidGender) {
        editor = prefs.edit();
        editor.putString("KidGender", kidGender);
        editor.commit();
    }


    public String getTeacherId() {
        return prefs.getString("TeacherId", null);
    }

    public void setTeacherId(String teacherId) {
        editor = prefs.edit();
        editor.putString("TeacherId", teacherId);
        editor.commit();
    }

    public String getTotalRateCount() {
        return prefs.getString("TotalRateCount", null);
    }

    public void setTotalRateCount(String totalRateCount) {
        editor = prefs.edit();
        editor.putString("TotalRateCount", totalRateCount);
        editor.commit();
    }

    public String getTeacherProfileUrl() {
        return prefs.getString("TeacherProfileUrl", null);
    }

    public void setTeacherProfileUrl(String teacherProfileUrl) {
        editor = prefs.edit();
        editor.putString("TeacherProfileUrl", teacherProfileUrl);
        editor.commit();
    }

    String TeacherProfileUrl;
}

