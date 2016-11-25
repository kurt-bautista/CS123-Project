package layout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.grpd.secb.mngr.Group;
import com.grpd.secb.mngr.Member;
import com.grpd.secb.mngr.MemberSelectDialog;
import com.grpd.secb.mngr.MemberStatRecord;
import com.grpd.secb.mngr.MemberViewAdapter;
import com.grpd.secb.mngr.NewMemberDialog;
import com.grpd.secb.mngr.NewStatDialog;
import com.grpd.secb.mngr.R;
import com.grpd.secb.mngr.Sport;
import com.grpd.secb.mngr.StatRecord;
import com.grpd.secb.mngr.StatRecordAdapter;
import com.grpd.secb.mngr.ViewGroupActivity;
import com.grpd.secb.mngr.ViewMemberActivity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewGroupFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String group_id;
    Realm realm;
    Group group;

    private OnFragmentInteractionListener mListener;

    public ViewGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ViewGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewGroupFragment newInstance(String param1) {
        ViewGroupFragment fragment = new ViewGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();

        if (getArguments() != null) {
            group_id = getArguments().getString(ARG_PARAM1);
            group = realm.where(Group.class).equalTo("id",group_id).findFirst();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v =  inflater.inflate(R.layout.fragment_view_group, container, false);
        final TabHost tabHost = (TabHost) v.findViewById(R.id.TabHost);
        tabHost.setup();
        TabHost.TabSpec tabs = tabHost.newTabSpec("tab1");
        tabs.setContent(R.id.tab1);
        tabs.setIndicator("Members");
        tabHost.addTab(tabs);
        tabs = tabHost.newTabSpec("tab2");
        tabs.setContent(R.id.tab2);
        tabs.setIndicator("Stats");
        tabHost.addTab(tabs);

        TextView nameView = (TextView) v.findViewById(R.id.nameView);
        TextView descView = (TextView) v.findViewById(R.id.descView);
        TextView sportView = (TextView) v.findViewById(R.id.sportView);

        String desc = "";

        if(group.getDescription().equals("")){

            desc = "No Description.";

        }
        else{

            desc = group.getDescription();

        }

        nameView.setText(group.getName());
        descView.setText(desc);
        sportView.setText(realm.where(Sport.class).equalTo("id",group.getSport_id()).findFirst().getName());

        final ListView lv = (ListView) v.findViewById(R.id.memberListView);
        lv.setAdapter(new MemberViewAdapter(v.getContext(),realm.where(Member.class).equalTo("group_id",group.getId()).findAll(),MemberViewAdapter.VIEW_GROUP));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == lv.getAdapter().getCount()-1){

                    NewMemberDialog d = new NewMemberDialog(v.getContext(),group,false,"");
                    d.show();

                }
                else{

                    Intent intent = new Intent(v.getContext(),ViewMemberActivity.class);
                    intent.putExtra("id",((MemberViewAdapter)lv.getAdapter()).getItem(i).getId());
                    startActivity(intent);
                }

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Dialog d = new MemberSelectDialog(v.getContext(),(MemberViewAdapter)lv.getAdapter(),i);
                d.show();
                return true;
            }
        });

        Button newStat = (Button) v.findViewById(R.id.newStatButton);
        newStat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Dialog d = new NewStatDialog(v.getContext(),group);
                tabHost.setCurrentTab(0);
                d.show();

            }
        });

        RealmResults<Member> memberList = realm.where(Member.class).equalTo("group_id",group.getId()).findAll();
        ArrayList<RealmResults<MemberStatRecord>> msrList = new ArrayList<>();
        ArrayList<RealmResults<StatRecord>> srList = new ArrayList<>();

        for(Member m : memberList){

            msrList.add(realm.where(MemberStatRecord.class).equalTo("member_id",m.getId()).findAll());

        }

        for(RealmResults<MemberStatRecord> resultList : msrList){

            for(MemberStatRecord result : resultList){

                srList.add(realm.where(StatRecord.class).equalTo("id",result.getStat_record_id()).findAll());

            }

        }

        RealmList<StatRecord> statRecords = new RealmList<>();

        for(RealmResults<StatRecord> srResults : srList){

            for(StatRecord result : srResults){

                if(!statRecords.contains(result)){

                    statRecords.add(result);

                }
            }

        }


        final ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.statRecordListView);
        final StatRecordAdapter adapter =new StatRecordAdapter(v.getContext(),statRecords);



        elv.setAdapter(adapter);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                RealmResults<Member> memberList = realm.where(Member.class).equalTo("group_id",group.getId()).findAll();
                ArrayList<RealmResults<MemberStatRecord>> msrList = new ArrayList<>();
                ArrayList<RealmResults<StatRecord>> srList = new ArrayList<>();

                for(Member m : memberList){

                    msrList.add(realm.where(MemberStatRecord.class).equalTo("member_id",m.getId()).findAll());

                }

                for(RealmResults<MemberStatRecord> resultList : msrList){

                    for(MemberStatRecord result : resultList){

                        srList.add(realm.where(StatRecord.class).equalTo("id",result.getStat_record_id()).findAll());

                    }

                }

                RealmList<StatRecord> statRecords = new RealmList<>();

                for(RealmResults<StatRecord> srResults : srList){

                    for(StatRecord result : srResults){

                        if(!statRecords.contains(result)){

                            statRecords.add(result);

                        }
                    }

                }

                adapter.getData().clear();
                adapter.getData().addAll(statRecords);

                elv.invalidateViews();
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
