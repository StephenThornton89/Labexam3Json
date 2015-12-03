import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/MinMax")
public class MinMax extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sts = "";
        String inputString = request.getParameter("num");
        try {
            System.out.println("Random nums are: "+inputString );
            JSONObject inputValues = new JSONObject(inputString);
            System.out.println("Numbers are:");


            double[] array = new double[5]; //new

             array[0]= inputValues.getDouble("one");
             array[1] = inputValues.getDouble("two");
             array[2]= inputValues.getDouble("three");
             array[3]= inputValues.getDouble("four");
             array[4]= inputValues.getDouble("five");
            //***********************************************************//

            System.out.print(array[0]);
            System.out.print(", "+array[1]);
            System.out.print(", " + array[2]);
            System.out.print(", "+array[3]);
            System.out.print(", "+array[4]);
            //***********************************************************//

            Arrays.sort(array);

            double max = array[0];
            double min = array[array.length-1];
            double sum = 0;

            for (double i : array) {
                sum += i;
            }
            JSONObject jsn = new JSONObject();
            jsn.put("max",max);
            jsn.put("min",min);
            jsn.put("sum",sum);

            PrintWriter out = response.getWriter();
            out.println(jsn);


        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        out.println(sts);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
