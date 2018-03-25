package com.gursimransinghhanspal.rove.data;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class SearchDiarySuggestion implements SearchSuggestion {
    private String mSearchQuery;
    private boolean mIsHistory = false;

    public SearchDiarySuggestion(String suggestion) {
        this.mSearchQuery = suggestion.toLowerCase();
    }

    public SearchDiarySuggestion(String suggestion, boolean isHistory) {
        this.mSearchQuery = suggestion.toLowerCase();
        this.mIsHistory = isHistory;
    }

    public SearchDiarySuggestion(Parcel source) {
        this.mSearchQuery = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mSearchQuery;
    }

    public static final Creator<SearchDiarySuggestion> CREATOR = new Creator<SearchDiarySuggestion>() {
        @Override
        public SearchDiarySuggestion createFromParcel(Parcel in) {
            return new SearchDiarySuggestion(in);
        }

        @Override
        public SearchDiarySuggestion[] newArray(int size) {
            return new SearchDiarySuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSearchQuery);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
