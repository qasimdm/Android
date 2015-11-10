package qasim.stepcounter;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFrag extends DialogFragment {

    Button btnDialogSetGoal;
    EditText editTextDialog;
    public DialogFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        btnDialogSetGoal = (Button)view.findViewById(R.id.btnDialogOk);
        editTextDialog = (EditText)view.findViewById(R.id.editViewDialog);
        btnDialogSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strGoal = editTextDialog.getText().toString();
                //strGoal.charAt(0);
                Log.i("TAG", "strGoal i fragment: " + strGoal);
                if(strGoal!=null && !strGoal.isEmpty()) {

                    MainActivity.goal = Double.parseDouble(strGoal);
                    if(MainActivity.goal <= MainActivity.counter) {
                        MainActivity.counter = 0;
                        ((MainActivity) getActivity()).stepView.setText("" + (int) MainActivity.counter);
                        ((MainActivity) getActivity()).progressBar.setProgress((int)(MainActivity.counter/MainActivity.goal)*100);
                    }

                }

                Log.i("TAG", "Goal i fragment: " + MainActivity.goal);
                getDialog().dismiss();
            }
        });
        return view;
    }


}
