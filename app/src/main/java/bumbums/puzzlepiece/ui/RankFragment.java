package bumbums.puzzlepiece.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bumbums.puzzlepiece.R;
import bumbums.puzzlepiece.model.Friend;
import bumbums.puzzlepiece.ui.adapter.RankRecyclerViewAdapter;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by 한승범 on 2017-02-11.
 */

public class RankFragment extends android.support.v4.app.Fragment {

    private RecyclerView mRecyclerView;
    private Realm realm;
    private RankRecyclerViewAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Friend> friends = realm.where(Friend.class).findAll();
                for(int i=0; i<friends.size();i++){
                    friends.get(i).setPuzzleNum(friends.get(i).getPuzzles().size());
                }
            }
        });

        mAdapter = new RankRecyclerViewAdapter(this, realm.where(Friend.class).findAllAsync().sort("puzzleNum", Sort.DESCENDING));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container,false);
        mRecyclerView =(RecyclerView) view.findViewById(R.id.rv_rank);
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
    }
}
