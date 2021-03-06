package br.usp.ime.mac5743.ep1.seminarioime.api;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.usp.ime.mac5743.ep1.seminarioime.pojo.Professor;
import br.usp.ime.mac5743.ep1.seminarioime.pojo.Seminar;
import br.usp.ime.mac5743.ep1.seminarioime.pojo.Student;
import br.usp.ime.mac5743.ep1.seminarioime.pojo.User;
import br.usp.ime.mac5743.ep1.seminarioime.util.Roles;

/**
 * Created by aderleifilho on 28/04/17.
 * <p>
 * This class is responsible for handling all Rest requests made by this application.
 * This class will always return a map with the following structure:
 * <p>
 * All the APIs URL are stored in an interface class called RestRoutes.java
 * <p>
 * Map<String, String>
 * ResponseCode : ResponseCode for the request
 * Payload : Full Json response for the request
 */

public class RestAPIUtil extends AsyncTask<Object, Void, JSONObject> {

    private static RestAPIUtil getInstance() {
        return new RestAPIUtil();
    }

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static boolean isTesting = false;

    public RestAPIUtil(boolean pIsTesting) {
        isTesting = pIsTesting;
    }

    public RestAPIUtil() {
    }

    /**
     * Login APIs
     */

    public static boolean login(String pNusp, String pPassword, String role) {
        JSONObject responseFromAsyncTask;
        URL url;
        Student student = new Student();
        student.setNusp(pNusp);
        student.setPass(pPassword);
        try {
            if (role.equalsIgnoreCase(Roles.PROFESSOR.toString())) {
                url = new URL(RestRoutes.LOGIN_PROFESSOR);
            } else {
                url = new URL(RestRoutes.LOGIN_STUDENT);
            }
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            } else {
                return pNusp.equals("TEST") ? true : false;
            }

            if (responseFromAsyncTask != null && responseFromAsyncTask.getBoolean("success")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * Student APIs
     */

    public static JSONObject getAllStudents() {
        URL url;
        try {
            url = new URL(RestRoutes.GET_ALL_STUDENTS);
            if (!isTesting) {
                return getInstance().execute(url, GET).get();
            } else {
                JSONObject student = new JSONObject();
                return student;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getStudent(String pNusp) {
        URL url;
        try {
            url = new URL(String.format(RestRoutes.GET_STUDENT, pNusp));
            if (!isTesting) {
                return getInstance().execute(url, GET).get();
            } else {
                JSONObject student = new JSONObject();
                return pNusp.equals("TEST") ? student : null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String addStudent(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Student student = new Student(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.ADD_STUDENT);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            } else {
                return pNusp.equals("TEST") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    public static String editStudent(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Student student = new Student(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.EDIT_STUDENT);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            } else {
                return pNusp.equals("TEST") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    public static String deleteStudent(String pNusp) {
        JSONObject responseFromAsyncTask;
        URL url;
        Student student = new Student();
        student.setNusp(pNusp);

        try {
            url = new URL(RestRoutes.DELETE_STUDENT);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            } else {
                return pNusp.equals("TEST") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    /**
     * Professor APIs
     */

    public static JSONObject getAllProfessors() {
        URL url;
        try {
            url = new URL(RestRoutes.GET_ALL_PROFESSORS);
            if (!isTesting) {
                return getInstance().execute(url, GET).get();
            } else {
                JSONObject professor = new JSONObject();
                return professor;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static JSONObject getProfessor(String pNusp) {
        URL url;
        try {
            url = new URL(String.format(RestRoutes.GET_PROFESSOR, pNusp));
            if (!isTesting) {
                return getInstance().execute(url, GET).get();
            } else {
                JSONObject professor = new JSONObject();
                return pNusp.equals("TEST") ? professor : null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String addProfessor(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Professor professor = new Professor(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.ADD_PROFESSOR);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, professor).get();
            } else {
                return pNusp.equals("TEST") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    public static String editProfessor(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Professor professor = new Professor(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.EDIT_PROFESSOR);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, professor).get();
            } else {
                return pNusp.equals("TEST") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    public static String deleteProfessor(String pNusp) {
        JSONObject responseFromAsyncTask;
        URL url;
        Professor professor = new Professor();
        professor.setNusp(pNusp);

        try {
            url = new URL(RestRoutes.DELETE_PROFESSOR);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, professor).get();
            } else {
                return pNusp.equals("TEST") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    /**
     * Seminar APIs
     */

    public static JSONObject getAllSeminars() {
        URL url;
        try {
            url = new URL(RestRoutes.GET_ALL_SEMINARS);
            if (!isTesting) {
                return getInstance().execute(url, GET).get();
            } else {
                JSONObject seminars = new JSONObject();
                return seminars;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getSeminar(String pSeminarId) {
        URL url;
        try {
            url = new URL(String.format(RestRoutes.GET_SEMINAR, pSeminarId));
            if (!isTesting) {
                return getInstance().execute(url, GET).get();
            } else {
                JSONObject professor = new JSONObject();
                return pSeminarId.equals("TEST") ? professor : null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean addSeminar(String pSeminarName) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar(pSeminarName);

        try {
            url = new URL(RestRoutes.ADD_SEMINAR);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            } else {
                return pSeminarName.equals("SEMINAR") ? true : false;
            }
            if (responseFromAsyncTask != null && responseFromAsyncTask.getBoolean("success")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static String editSeminar(String pSeminarId, String pSeminarName) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar(pSeminarId, pSeminarName);

        try {
            url = new URL(RestRoutes.EDIT_SEMINAR);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            } else {
                return pSeminarId.equals("SEMINAR") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    public static String deleteSeminar(String pSeminarId) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar();
        seminar.setSeminarId(pSeminarId);

        try {
            url = new URL(RestRoutes.DELETE_SEMINAR);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            } else {
                return pSeminarId.equals("SEMINAR") ? null : "Error";
            }
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            if (e.getMessage() != null) {
                return e.getMessage();
            }
            return e.toString();
        }
        return null;
    }

    /**
     * Attendance APIs
     */

    public static boolean confirmAttendance(String nusp, String pSeminarId) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar(nusp);
        seminar.setSeminarId(pSeminarId);
        Student student = new Student();
        student.setNusp(nusp);

        try {
            url = new URL(RestRoutes.SUBMIT_ATTENDANCE);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, seminar, student).get();
            } else {
                return pSeminarId.equals("SEMINAR") ? true : false;
            }
            if (responseFromAsyncTask != null && responseFromAsyncTask.getBoolean("success")) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static ArrayList<JSONObject> getAttendanceList(String pSeminarId) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar();
        seminar.setSeminarId(pSeminarId);
        ArrayList<JSONObject> studentList = new ArrayList<>();

        try {
            url = new URL(RestRoutes.GET_ATTENDANCE_LIST);
            if (!isTesting) {
                responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            } else {
                return pSeminarId.equals("SEMINAR") ? studentList : null;
            }
            if (responseFromAsyncTask != null && responseFromAsyncTask.getBoolean("success")) {
                JSONArray ja = responseFromAsyncTask.getJSONArray("data");
                if (ja != null) {
                    for (int i = 0; i < ja.length(); i++) {
                        studentList.add(ja.getJSONObject(i));
                    }
                }
            }
            return studentList;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected JSONObject doInBackground(Object... objects) {
        User userRequestInfo = null;
        Seminar seminarRequestInfo = null;
        URL url = null;
        String requestMethod = null;
        JSONObject joResponse;

        for (Object object : objects) {
            if (object instanceof String) {
                if (((String) object).equalsIgnoreCase(GET) || ((String) object).equalsIgnoreCase(POST)) {
                    requestMethod = (String) object;
                }
            }

            if (object instanceof User) {
                userRequestInfo = (User) object;
            } else if (object instanceof Seminar) {
                seminarRequestInfo = (Seminar) object;
            }

            if (object instanceof URL) {
                url = (URL) object;
            }
        }

        try {

            /*
             * HTTP URL Connection
             */

            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.setRequestMethod(requestMethod);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            /*
             * Body
             */

            if (requestMethod.equalsIgnoreCase(POST)) {

                StringBuilder sb = new StringBuilder();

                if (userRequestInfo != null) {
                    if (userRequestInfo.getNusp() != null) {
                        sb.append("nusp=");
                        sb.append(userRequestInfo.getNusp());
                    }

                    if (userRequestInfo.getName() != null) {
                        if (sb.toString() != null && !sb.toString().equalsIgnoreCase("")) {
                            sb.append("&");
                        }
                        sb.append("name=");
                        sb.append(userRequestInfo.getName());
                    }

                    if (userRequestInfo.getPass() != null) {
                        if (sb.toString() != null && !sb.toString().equalsIgnoreCase("")) {
                            sb.append("&");
                        }
                        sb.append("pass=");
                        sb.append(userRequestInfo.getPass());
                    }
                }

                if (seminarRequestInfo != null) {
                    if (seminarRequestInfo.getSeminarId() != null) {
                        if (sb.toString() != null && !sb.toString().equalsIgnoreCase("")) {
                            sb.append("&");
                        }
                        if (url.getPath().contains("attendence")) {
                            sb.append("seminar_id=");
                        } else {
                            sb.append("id=");
                        }
                        sb.append(seminarRequestInfo.getSeminarId());
                    }

                    if (seminarRequestInfo.getSeminarName() != null) {
                        if (sb.toString() != null && !sb.toString().equalsIgnoreCase("")) {
                            sb.append("&");
                        }
                        sb.append("name=");
                        sb.append(seminarRequestInfo.getSeminarName());
                    }
                }

                String urlParameters = sb.toString();
                byte[] outputBytes = urlParameters.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputBytes);
            }


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String str;
            while ((str = reader.readLine()) != null) {
                json.append(str).append("\n");
            }
            reader.close();
            joResponse = new JSONObject(json.toString());
            return joResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
