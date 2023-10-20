package fms;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log_File_Out 
{
    public void logFileOut(String inFilePath, String massage)
    {
        BufferedWriter buffered;
        Calendar timeOut = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
        String strokWork;

        strokWork = format1.format(timeOut.getTime());
        strokWork += "    " + massage + "\n";
        try {
            FileOutputStream fos = new FileOutputStream(inFilePath, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "windows-1251");
            buffered = new BufferedWriter(osw);
            buffered.write(strokWork);
            buffered.flush();
            buffered.close();
            osw.close();
        } catch (Exception e) {}
    }
}
