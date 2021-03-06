package com.example.habii.nbmreadingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BooksFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BooksFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore firebaseFirestore;

    List<Classes> bookList;

    TeachersBooksAdapter teachersBooksAdapter;

    RecyclerView recyclerView;

    private FirebaseAuth mAuth;

    String post_id;

    View Root;

    private OnFragmentInteractionListener mListener;

    public BooksFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BooksFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static BooksFrag newInstance(String param1, String param2) {
        BooksFrag fragment = new BooksFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Root = inflater.inflate(R.layout.fragment_books, container, false);

        Bundle bundle = getArguments();

        post_id = bundle.getString("ClassID");

        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView)Root.findViewById(R.id.booklist);


        bookList = new ArrayList<>();


        teachersBooksAdapter = new TeachersBooksAdapter(bookList,container.getContext());

        mAuth = FirebaseAuth.getInstance();


        Query fireQuery = firebaseFirestore.collection("Teachers").document(mAuth.getCurrentUser().getUid()).collection("Class")
                .document(post_id).collection("Books").orderBy("name",Query.Direction.ASCENDING);
        fireQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {


                if(e != null){}


                for(DocumentChange documentChange : documentSnapshots.getDocumentChanges()){

                    if(documentChange.getType() == DocumentChange.Type.ADDED ){



                        String postid = documentChange.getDocument().getId();

                        Classes classess = documentChange.getDocument().toObject(Classes.class).withID(postid,post_id,"");

                        bookList.add(classess);

                        teachersBooksAdapter.notifyDataSetChanged();



                    }


                }

            }
        });

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(container.getContext(), R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);

        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(teachersBooksAdapter);




        return Root;
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
