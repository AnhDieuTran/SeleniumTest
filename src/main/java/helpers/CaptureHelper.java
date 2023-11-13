package helpers;
import atu.testrecorder.ATUTestRecorder;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureHelper {
    private ATUTestRecorder recorder;
    static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
    static Date date = new Date();


    public void startRecordATU(String videoName) throws Exception {
        recorder = new ATUTestRecorder("./reports/record/", videoName + "-" + dateFormat.format(date), false);
        recorder.start();
    }

    public void stopRecordATU() throws Exception {
        recorder.stop();
    }
}
