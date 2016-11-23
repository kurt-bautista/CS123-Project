package com.grpd.secb.mngr;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by Sean on 11/22/2016.
 */

public class NewMemberDialog extends Dialog{

    private Group group;

    public NewMemberDialog(Context c, Group g) {
        super(c);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        group = g;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_member_dialog);

        final TextView birthdayView = (TextView) findViewById(R.id.newMemberBirthdayTextView);
        final EditText ageView = (EditText) findViewById(R.id.newMemberAgeEditText);
        Button pick = (Button) findViewById(R.id.datePicker);
        pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener mListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar c = Calendar.getInstance();

                        birthdayView.setText((i1+1)+ "/" + i2 + "/" + i);

                        int age = c.get(Calendar.YEAR)-i;

                        if(i1 >= c.get(Calendar.MONTH) && i2 > c.get(Calendar.DAY_OF_MONTH)){

                            age-=1;

                        }

                        ageView.setText(Integer.toString(age));
                    }
                };

                Calendar c = Calendar.getInstance();

                DatePickerDialog d = new DatePickerDialog(getContext(),mListener,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                d.show();

            }
        });



        Button ok = (Button) findViewById(R.id.createMemberButton);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText name = (EditText)findViewById(R.id.newMemberNameEditText);
                EditText height = (EditText)findViewById(R.id.newMemberHeightEditText);
                EditText weight = (EditText)findViewById(R.id.newMemberWeightEditText);
                EditText age = (EditText)findViewById(R.id.newMemberAgeEditText);
                EditText contactNumber = (EditText)findViewById(R.id.newMemberContactEditText);
                TextView birthday = (TextView)findViewById(R.id.newMemberBirthdayTextView);

                if(name.getText().toString().trim().equals("")
                        || height.getText().toString().trim().equals("")
                        || weight.getText().toString().trim().equals("")
                        || age.getText().toString().trim().equals("")
                        || contactNumber.getText().toString().trim().equals("")
                        || birthday.getText().toString().trim().equals("XX/XX")){

                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Missing Fields")
                            .setMessage("Please fill in all fields.")
                            .setPositiveButton("OK", null)
                            .show();

                }
                else{

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    Member m = realm.createObject(Member.class);
                    m.setId(UUID.randomUUID().toString());
                    m.setName(name.getText().toString());
                    m.setGroup_id(group.getId());
                    m.setHeight(height.getText().toString());
                    m.setWeight(weight.getText().toString());
                    m.setAge(Integer.parseInt(age.getText().toString()));
                    m.setBirthday(birthday.getText().toString());
                    m.setOther_description(((EditText)findViewById(R.id.newMemberDescriptionEditText)).getText().toString());
                    realm.commitTransaction();
                    NewMemberDialog.this.dismiss();
                }

            }
        });

        Button cancel = (Button) findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewMemberDialog.this.dismiss();
            }
        });
    }
}
