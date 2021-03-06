package com.example.android_gearbox_recyclerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NumberPickerVirticalActivity extends Activity{

    private static final String TAG = MainActivity.class.getSimpleName();

    public float firstItemHeightDate;
    public float paddingDate;
    public float itemHeightDate;
    public int allPixelsDate;
    public int finalHeightDate;
    private DateAdapter dateAdapter;
    private ArrayList<LabelerDate> labelerDates = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_picker_vertical);

        getRecyclerviewDate();
    }


    public void getRecyclerviewDate() {
        final RecyclerView recyclerViewDate = (RecyclerView) findViewById(R.id.recycler_view);
        if (recyclerViewDate != null) {
            recyclerViewDate.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setDateValue();
                }
            }, 300);
            /*recyclerViewDate.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerViewDate.smoothScrollToPosition(dateAdapter.getItemCount()-1);
                    setDateValue();
                }
            }, 5000);*/
        }
        ViewTreeObserver vtoDate = recyclerViewDate.getViewTreeObserver();
        vtoDate.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerViewDate.getViewTreeObserver().removeOnPreDrawListener(this);
                finalHeightDate = recyclerViewDate.getMeasuredHeight();
                itemHeightDate = getResources().getDimension(R.dimen.item_dob_height);
                paddingDate = (finalHeightDate - itemHeightDate) / 2;
                firstItemHeightDate = paddingDate;
                allPixelsDate = 0;

                final LinearLayoutManager dateLayoutManager = new LinearLayoutManager(getApplicationContext());
                dateLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewDate.setLayoutManager(dateLayoutManager);
                recyclerViewDate.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        synchronized (this) {
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                calculatePositionAndScrollDate(recyclerView);
                            }
                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        allPixelsDate += dy;
                    }
                });
                if (labelerDates == null) {
                    labelerDates = new ArrayList<>();
                }
                genLabelerDate();
                dateAdapter = new DateAdapter(labelerDates, (int) firstItemHeightDate);
                recyclerViewDate.setAdapter(dateAdapter);
                dateAdapter.setSelecteditem(dateAdapter.getItemCount() - 1);
                return true;
            }
        });
    }

    private void genLabelerDate() {
        for (int i = 0; i < 3; i++) {
            LabelerDate labelerDate = new LabelerDate();
            labelerDate.setNumber(Integer.toString(i));
            labelerDates.add(labelerDate);

            if (i == 0 || i == 2) {
                labelerDate.setType(DateAdapter.VIEW_TYPE_PADDING);
            } else {
                labelerDate.setType(DateAdapter.VIEW_TYPE_ITEM);
            }
        }
    }
    /* this if most important, if expectedPositionDate < 0 recyclerView will return to nearest item*/

    private void calculatePositionAndScrollDate(RecyclerView recyclerView) {
        int expectedPositionDate = Math.round((allPixelsDate + paddingDate - firstItemHeightDate) / itemHeightDate);

        if (expectedPositionDate == -1) {
            expectedPositionDate = 0;
        } else if (expectedPositionDate >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPositionDate--;
        }
        scrollListToPositionDate(recyclerView, expectedPositionDate);

    }

    /* this if most important, if expectedPositionDate < 0 recyclerView will return to nearest item*/
    private void scrollListToPositionDate(RecyclerView recyclerView, int expectedPositionDate) {
        float targetScrollPosDate = expectedPositionDate * itemHeightDate + firstItemHeightDate - paddingDate;
        float missingPxDate = targetScrollPosDate - allPixelsDate;
        if (missingPxDate != 0) {
            recyclerView.smoothScrollBy(0, (int) missingPxDate);
        }
        setDateValue();
    }

    //
    private void setDateValue() {
        int expectedPositionDateColor = Math.round((allPixelsDate + paddingDate - firstItemHeightDate) / itemHeightDate);
        int setColorDate = expectedPositionDateColor + 1;
        //set color here
        dateAdapter.setSelecteditem(setColorDate);
    }


    public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {
        private ArrayList<LabelerDate> dateDataList;


        private static final int VIEW_TYPE_PADDING = 1;
        private static final int VIEW_TYPE_ITEM = 2;
        private int paddingHeightDate = 0;

        private int selectedItem = -1;

        public DateAdapter(ArrayList<LabelerDate> dateData, int paddingHeightDate) {
            this.dateDataList = dateData;
            this.paddingHeightDate = paddingHeightDate;

        }


        @Override
        public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,
                        parent, false);
                return new DateViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,
                        parent, false);

                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                layoutParams.height = paddingHeightDate;
                view.setLayoutParams(layoutParams);
                return new DateViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(DateViewHolder holder, int position) {
            LabelerDate labelerDate = dateDataList.get(position);
            if (getItemViewType(position) == VIEW_TYPE_ITEM) {
                holder.tvDate.setText(labelerDate.getNumber());
                holder.tvDate.setVisibility(View.VISIBLE);

                Log.d(TAG, "default " + position + ", selected " + selectedItem);
                if (position == selectedItem) {
                    Log.d(TAG, "center" + position);
                    holder.tvDate.setTextColor(Color.parseColor("#76FF03"));
                    holder.tvDate.setTextSize(35);

                } else {
                    holder.tvDate.setTextColor(Color.BLACK);
                    holder.tvDate.setTextSize(18);
                }
            } else {
                holder.tvDate.setVisibility(View.INVISIBLE);
            }
        }

        public void setSelecteditem(int selecteditem) {
            this.selectedItem = selecteditem;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dateDataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            LabelerDate labelerDate = dateDataList.get(position);
            if (labelerDate.getType() == VIEW_TYPE_PADDING) {
                return VIEW_TYPE_PADDING;
            } else {
                return VIEW_TYPE_ITEM;
            }

        }


        public class DateViewHolder extends RecyclerView.ViewHolder {
            public TextView tvDate;

            public DateViewHolder(View itemView) {
                super(itemView);
                tvDate = (TextView) itemView.findViewById(R.id.tv_year);
            }
        }
    }

    private class LabelerDate {
        private int type;
        private String number;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
