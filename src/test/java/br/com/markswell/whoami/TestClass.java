package br.com.markswell.whoami;

import org.bytedeco.javacv.FrameGrabber;
import org.junit.jupiter.api.Test;
import br.com.markswell.whoami.image.recognition.ImageRecognition;

import java.net.URISyntaxException;

public class TestClass {

    @Test
    public void initialTest() throws FrameGrabber.Exception, URISyntaxException {
        ImageRecognition imageRecognition = new ImageRecognition();
        imageRecognition.capture();
    }
}
