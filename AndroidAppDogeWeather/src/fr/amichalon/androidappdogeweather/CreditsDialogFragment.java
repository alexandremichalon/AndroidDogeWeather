/**
 * 
 */
package fr.amichalon.androidappdogeweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * @author alexandre.michalon
 *
 */
public class CreditsDialogFragment extends DialogFragment 
{
	

	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		Activity activity		= getActivity();
		LayoutInflater inflater	= activity.getLayoutInflater();
    	View creditsView		= inflater.inflate(R.layout.doge_credits, null);
    	
    	makeTextViewLinksClickable(creditsView);
    	
    	creditsView.setBackgroundResource(R.color.credits_bgcolor);
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK);
    	
    	builder.setView(creditsView);
    	builder.setTitle(R.string.credits_title);
    	builder.setNeutralButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// only the dismiss is done
			}
		});
    	
    	return builder.create();
    }
	
	
	
	private void makeTextViewLinksClickable(View view)
	{
		MovementMethod linkMovementMethod = LinkMovementMethod.getInstance();
		
    	TextView textCredits2		= (TextView) view.findViewById(R.id.TextCredits2); 
    	TextView textCredits3		= (TextView) view.findViewById(R.id.TextCredits3); 
    	TextView textCreditsLicense	= (TextView) view.findViewById(R.id.TextCreditsLicense); 
    	
    	textCredits2.setMovementMethod(linkMovementMethod);
    	textCredits3.setMovementMethod(linkMovementMethod);
    	textCreditsLicense.setMovementMethod(linkMovementMethod);
	}

}
