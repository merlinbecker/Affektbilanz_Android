package de.merlinbecker.affektbilanz;

import android.app.Application;
import org.acra.*;
import org.acra.annotation.*;
@ReportsCrashes(
	    formKey = "", 
	    formUri = "http://work.chillergraphics.de/android_affektbilanz/"
	)

public class Affektbilanz extends Application {
	@Override
    public void onCreate() {
        super.onCreate();
        ACRA.init(this);
    }
}