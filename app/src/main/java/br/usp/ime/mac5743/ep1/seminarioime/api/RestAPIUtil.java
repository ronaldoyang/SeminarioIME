package br.usp.ime.mac5743.ep1.seminarioime.api;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.usp.ime.mac5743.ep1.seminarioime.R;
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

    private static final String CLASS_NAME = "RestAPIUtil";
    private static final String GET = "GET";
    private static final String POST = "POST";

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
            responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            Log.d(CLASS_NAME, responseFromAsyncTask.toString());
            if (responseFromAsyncTask != null && responseFromAsyncTask.getBoolean("success")) {
                return true;
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format("An error occurred when adding a Student. Error message: %s", e.getMessage()));
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
            return getInstance().execute(url, GET).get();
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_S005), e.getMessage()));
            return null;
        }
    }

    public static JSONObject getStudent(String pNusp) {
        Log.d(CLASS_NAME, String.format(RestRoutes.GET_STUDENT, pNusp));
        URL url;
        try {
            url = new URL(String.format(RestRoutes.GET_STUDENT, pNusp));
            return getInstance().execute(url, GET).get();
        } catch (Exception e) {
            Log.d(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_S004), e.getMessage()));
            return null;
        }
    }

    public static String addStudent(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Student student = new Student(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.ADD_STUDENT);
            responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_S001), e.getMessage()));
            return e.getMessage();
        }
        return null;
    }

    public static String editStudent(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Student student = new Student(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.EDIT_STUDENT);
            responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_S002), e.getMessage()));
            return e.getMessage();
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
            responseFromAsyncTask = getInstance().execute(url, POST, student).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_S003), e.getMessage()));
            return e.getMessage();
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
            return getInstance().execute(url, GET).get();
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_P005), e.getMessage()));
            return null;
        }
    }

    public static JSONObject getProfessor(String pNusp) {
        URL url;
        try {
            url = new URL(String.format(RestRoutes.GET_PROFESSOR, pNusp));
            return getInstance().execute(url, GET).get();
        } catch (Exception e) {
            Log.d(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_P004), e.getMessage()));
            return null;
        }
    }

    public static String addProfessor(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Professor professor = new Professor(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.ADD_PROFESSOR);
            responseFromAsyncTask = getInstance().execute(url, POST, professor).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_P001), e.getMessage()));
            return e.getMessage();
        }
        return null;
    }

    public static String editProfessor(String pNusp, String pName, String pPassword) {
        JSONObject responseFromAsyncTask;
        URL url;
        Professor professor = new Professor(pNusp, pName, pPassword);

        try {
            url = new URL(RestRoutes.EDIT_PROFESSOR);
            responseFromAsyncTask = getInstance().execute(url, POST, professor).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_P002), e.getMessage()));
            return e.getMessage();
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
            responseFromAsyncTask = getInstance().execute(url, POST, professor).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_P003), e.getMessage()));
            return e.getMessage();
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
            return getInstance().execute(url, GET).get();
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_SEM005), e.getMessage()));
            return null;
        }
    }

    public static JSONObject getSeminar(String pSeminarId) {
        Log.d(CLASS_NAME, String.format(RestRoutes.GET_SEMINAR, pSeminarId));
        URL url;
        try {
            url = new URL(String.format(RestRoutes.GET_SEMINAR, pSeminarId));
            return getInstance().execute(url, GET).get();
        } catch (Exception e) {
            Log.d(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_SEM004), e.getMessage()));
            return null;
        }
    }

    public static String addSeminar(String pSeminarName) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar(pSeminarName);

        try {
            url = new URL(RestRoutes.ADD_SEMINAR);
            responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_SEM001), e.getMessage()));
            return e.getMessage();
        }
        return null;
    }

    public static String editSeminar(String pSeminarId, String pSeminarName) {
        JSONObject responseFromAsyncTask;
        URL url;
        Seminar seminar = new Seminar(pSeminarId, pSeminarName);

        try {
            url = new URL(RestRoutes.EDIT_SEMINAR);
            responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_SEM002), e.getMessage()));
            return e.getMessage();
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
            responseFromAsyncTask = getInstance().execute(url, POST, seminar).get();
            if (responseFromAsyncTask != null && !responseFromAsyncTask.getBoolean("success")) {
                return responseFromAsyncTask.getString("message");
            }
        } catch (Exception e) {
            Log.e(CLASS_NAME, String.format(Resources.getSystem().getString(R.string.ERROR_API_SEM003), e.getMessage()));
            return e.getMessage();
        }
        return null;
    }

    /**
     * Attendance APIs
     */

    @Override
    protected JSONObject doInBackground(Object... objects) {
        Log.i(CLASS_NAME, "[BEGIN] doInBackground");

        User userRequestInfo = null;
        Seminar seminarRequestIndo = null;
        URL url = null;
        String requestMethod = null;
        JSONObject joResponse = null;

        for (Object object : objects) {
            if (object instanceof String) {
                if (((String) object).equalsIgnoreCase(GET) || ((String) object).equalsIgnoreCase(POST)) {
                    requestMethod = (String) object;
                }
            }

            if (object instanceof User) {
                userRequestInfo = (User) object;
            } else if (object instanceof Seminar) {
                seminarRequestIndo = (Seminar) object;
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
            Log.i(CLASS_NAME, url.toString());
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
                        if (sb.toString() != null && sb.toString() != "") {
                            sb.append("&");
                        }
                        sb.append("name=");
                        sb.append(userRequestInfo.getName());
                    }

                    if (userRequestInfo.getPass() != null) {
                        if (sb.toString() != null && sb.toString() != "") {
                            sb.append("&");
                        }
                        sb.append("pass=");
                        sb.append(userRequestInfo.getPass());
                    }
                } else if (seminarRequestIndo != null) {
                    if (seminarRequestIndo.getSeminarId() != null) {
                        sb.append("id=");
                        sb.append(seminarRequestIndo.getSeminarId());
                    }

                    if (seminarRequestIndo.getSeminarName() != null) {
                        if (sb.toString() != null && sb.toString() != "") {
                            sb.append("&");
                        }
                        sb.append("name=");
                        sb.append(seminarRequestIndo.getSeminarName());
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

        Log.i(CLASS_NAME, "[END] doInBackground");
        return null;
    }
}