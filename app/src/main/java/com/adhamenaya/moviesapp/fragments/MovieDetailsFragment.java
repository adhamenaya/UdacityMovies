package com.adhamenaya.moviesapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adhamenaya.moviesapp.R;
import com.adhamenaya.moviesapp.activities.MainActivity;
import com.adhamenaya.moviesapp.operations.MovieCreditsOps;
import com.adhamenaya.moviesapp.operations.MovieReviewsOps;
import com.adhamenaya.moviesapp.operations.MovieVideosOps;
import com.adhamenaya.moviesapp.retrofit.MovieCastData;
import com.adhamenaya.moviesapp.retrofit.MovieCrewData;
import com.adhamenaya.moviesapp.retrofit.MovieData;
import com.adhamenaya.moviesapp.retrofit.MovieReviewData;
import com.adhamenaya.moviesapp.retrofit.MovieVideoData;
import com.adhamenaya.moviesapp.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsFragment extends Fragment {

    private MoviesFragment.OnMovieSelectedListener mOnMovieSelectedListener;
    private MoviesFragment.OnFragmentAttached mOnFragmentAttached;
    private TextView overviewTV, voteAvgTV, releaseDateTV,
            voteCountTV, budgetTV, revenueTV, runtimeTV,
            noVideosTV, noCastTV, noCrewTV, noReviewsTV;
    private ImageView posterIV;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private MovieReviewsOps mMovieReviewsOps;
    private MovieVideosOps mMovieVideosOps;
    private MovieCreditsOps mMovieCreditsOps;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Picasso mPicasso;
    private LinearLayout mMovieReviewsLL, mMovieVideosLL, mMovieCrewLL, mMovieCastLL;
    private boolean mDisplayMovie = false;
    private MovieData mMovie;
    private FloatingActionButton mFloatingActionButton;
    private Button mShareBT;

    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        return fragment;
    }

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retain the state of the fragment after the configuration changes
        setRetainInstance(true);

        mPicasso = Picasso.with(getActivity());

        // Ops object for the review of the movie
        mMovieReviewsOps = new MovieReviewsOps(this);
        mMovieVideosOps = new MovieVideosOps(this);
        mMovieCreditsOps = new MovieCreditsOps(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        initUI(view);
        return view;
    }

    public void setDisplayMovies(MovieData movie) {

        //Set the displayed list of movies
        mMovie = movie;
        // Set the display flag
        mDisplayMovie = true;
    }

    private void initUI(View view) {

        setupFloatingActionButton(view);

        mCoordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.Coordinator_Layout);
        mAppBarLayout = (AppBarLayout) view.findViewById(R.id.AppBar_Layout);
        voteAvgTV = (TextView) view.findViewById(R.id.voteAvgTV);
        voteCountTV = (TextView) view.findViewById(R.id.voteCountTV);
        revenueTV = (TextView) view.findViewById(R.id.revenueTV);
        budgetTV = (TextView) view.findViewById(R.id.budgetTV);
        releaseDateTV = (TextView) view.findViewById(R.id.releaseDateTV);
        runtimeTV = (TextView) view.findViewById(R.id.runtimeTV);
        posterIV = (ImageView) view.findViewById(R.id.posterIV);
        mCollapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        overviewTV = (TextView) view.findViewById(R.id.overviewTV);
        mMovieReviewsLL = (LinearLayout) view.findViewById(R.id.movieReviewsLL);
        mMovieVideosLL = (LinearLayout) view.findViewById(R.id.movieVideosLL);
        mMovieCrewLL = (LinearLayout) view.findViewById(R.id.movieCrewLL);
        mMovieCastLL = (LinearLayout) view.findViewById(R.id.movieCastLL);
        noVideosTV = (TextView) view.findViewById(R.id.noVideosTV);
        noCastTV = (TextView) view.findViewById(R.id.noCastTV);
        noCrewTV = (TextView) view.findViewById(R.id.noCrewTV);
        noReviewsTV = (TextView) view.findViewById(R.id.noReviewsTV);
        mShareBT = (Button) view.findViewById(R.id.shareBT);
        // Check the display flag
        if (mDisplayMovie) {
            displayMovieDetails(mMovie);
            mDisplayMovie = false;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnFragmentAttached = (MoviesFragment.OnFragmentAttached) activity;
            mOnFragmentAttached.onAttach(this);

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMovieSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Display the details for the selected movie
     */
    public void displayMovieDetails(MovieData movieData) {
        if (movieData != null) {

            mMovie = movieData;

            // Clear the UI
            clearUI();

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
            AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
            int[] consumed = new int[2];
            if (behavior != null)
                behavior.onNestedFling(mCoordinatorLayout, mAppBarLayout, null, 0, -1000, true);

            mCollapsingToolbar.setTitle(movieData.getTitle());

            // Set the text for overview
            overviewTV.setText(movieData.getOverview());
            voteAvgTV.setText(String.valueOf(movieData.getVoteAvg()));
            voteCountTV.setText(Util.prepareLongNumber(movieData.getVoteCount()));
            revenueTV.setText(Util.prepareLongNumber(movieData.getRevenue()));
            budgetTV.setText(Util.prepareLongNumber(movieData.getBudget()));
            releaseDateTV.setText(movieData.getReleaseDate());
            runtimeTV.setText(String.valueOf(movieData.getRuntime()) + " min.");

            // Set the FAB icon
            if (mMovie.isFromFavourite())
                mFloatingActionButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_thumb_down_white_24dp));
            else
                mFloatingActionButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_thumb_up_white_24dp));

            // Load the poster image
            Util.loadImage(getActivity(), movieData.getPosterPath(), mPicasso, posterIV);

            if (!mMovie.isFromFavourite()) {
                // Get the movie reviews from the web services
                mMovieReviewsOps.executeGetMovieReviewsTask(movieData.getId());
                mMovieVideosOps.executeGetMovieVideosTask(movieData.getId());
                mMovieCreditsOps.executeGetMovieCreditsTask(movieData.getId());
            } else {
                // Display the content from the movie object coming from favourite database.
                displayMovieCast(mMovie.getMovieCastDataList());
                displayMovieCrew(mMovie.getMovieCrewDataList());
                displayMovieReviews(mMovie.getMovieReviewDataList());
                displayMovieVideos(mMovie.getMovieVideoDataList());
            }

            mShareBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share();
                }
            });
        }
    }

    private void share() {
        if (mMovie != null &&
                mMovie.getMovieVideoDataList() != null &&
                mMovie.getMovieVideoDataList().size() > 0) {
            String title = mMovie.getTitle();
            String url = "Watch this amazing movie (" + title + "): http://www.youtube.com/watch?v=" + mMovie.getMovieVideoDataList().get(0).getKey();

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            shareIntent.setType("video/*");
            startActivity(Intent.createChooser(shareIntent, "Share movie"));

        }
    }

    /**
     * Display the reviews for the selected movie
     */
    public void displayMovieReviews(List<MovieReviewData> movieReviews) {

        mMovieReviewsLL.removeAllViews();
        if (mMovie != null)
            mMovie.setMovieReviewDataList(movieReviews);

        if (movieReviews != null && movieReviews.size() > 0) {
            noReviewsTV.setVisibility(View.GONE);
            for (MovieReviewData movieReviewData : movieReviews) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.movie_review_row, mMovieReviewsLL, false);

                TextView mAuthorTV = (TextView) view.findViewById(R.id.authorTV);
                TextView mContentTV = (TextView) view.findViewById(R.id.contentTV);

                mAuthorTV.setText(movieReviewData.getAuthor());
                mContentTV.setText(movieReviewData.getContent());
                mMovieReviewsLL.addView(view);
            }
        } else
            noReviewsTV.setVisibility(View.VISIBLE);
    }

    /**
     * Display the videos for the selected movie
     */
    public void displayMovieVideos(List<MovieVideoData> movieVideos) {

        mMovieVideosLL.removeAllViews();
        if (mMovie != null)
            mMovie.setMovieVideoDataList(movieVideos);

        if (movieVideos != null && movieVideos.size() > 0) {
            noVideosTV.setVisibility(View.GONE);
            for (final MovieVideoData movieVideoData : movieVideos) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.movie_video_row, mMovieVideosLL, false);

                Button nameTV = (Button) view.findViewById(R.id.titleTV);
                nameTV.setText(movieVideoData.getName());
                nameTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + movieVideoData.getKey())));
                    }
                });

                mMovieVideosLL.addView(view);
            }
        } else
            noVideosTV.setVisibility(View.VISIBLE);
    }

    /**
     * Display the cast data for the selected movie
     */
    public void displayMovieCast(List<MovieCastData> movieCastDataList) {

        mMovieCastLL.removeAllViews();
        if (mMovie != null)
            mMovie.setMovieCastDataList(movieCastDataList);

        if (movieCastDataList != null && movieCastDataList.size() > 0) {
            noCastTV.setVisibility(View.GONE);
            for (MovieCastData movieCastData : movieCastDataList) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.movie_cast_row, mMovieCastLL, false);

                TextView nameTV = (TextView) view.findViewById(R.id.nameTV);
                TextView characterTV = (TextView) view.findViewById(R.id.characterTV);
                ImageView profileIV = (ImageView) view.findViewById(R.id.profileIV);

                nameTV.setText(movieCastData.getName());
                characterTV.setText(movieCastData.getCharacter());

                Util.loadImage(getActivity(), movieCastData.getProfilePath(), mPicasso, profileIV);

                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = 350;
                view.setLayoutParams(params);

                mMovieCastLL.addView(view);
            }
        } else {
            noCastTV.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Display the list of the crew data for the selected movie
     */
    public void displayMovieCrew(List<MovieCrewData> movieCrewDataList) {

        mMovieCrewLL.removeAllViews();
        if (mMovie != null)
            mMovie.setMovieCrewDataList(movieCrewDataList);

        if (movieCrewDataList != null && movieCrewDataList.size() > 0) {
            noCrewTV.setVisibility(View.GONE);
            for (MovieCrewData movieCrewData : movieCrewDataList) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.movie_crew_row, mMovieCrewLL, false);

                TextView nameTV = (TextView) view.findViewById(R.id.nameTV);
                TextView jobTV = (TextView) view.findViewById(R.id.jobTV);
                ImageView profileIV = (ImageView) view.findViewById(R.id.profileIV);

                nameTV.setText(movieCrewData.getName());
                jobTV.setText(movieCrewData.getJob());

                // Load profile image
                Util.loadImage(getActivity(), movieCrewData.getProfilePath(), mPicasso, profileIV);

                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.width = 350;
                view.setLayoutParams(params);

                mMovieCrewLL.addView(view);
            }
        } else
            noCrewTV.setVisibility(View.VISIBLE);

    }

    /**
     * Setup the floating button of add to favourite
     */
    private void setupFloatingActionButton(View view) {
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMovie.isFromFavourite()) {
                    boolean result = ((MainActivity) getActivity()).removeFavourite(mMovie);
                    if (result) {
                        Util.showToast(getActivity(), "Removed from Favourite!");
                        mFloatingActionButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_thumb_up_white_24dp));
                        mMovie.setFromFavourite(false);
                        refreshFavourite();
                    }
                } else {
                    boolean result = ((MainActivity) getActivity()).addFavourite(mMovie);
                    if (result) {
                        Util.showToast(getActivity(), "Added to Favourite!");
                        mFloatingActionButton.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_thumb_down_white_24dp));
                        mMovie.setFromFavourite(true);
                        refreshFavourite();
                        ((MainActivity) getActivity()).saveImages(getActivity(), mPicasso, mMovie);
                    }
                }
            }
        });
    }

    /**
     * Re-bind the list of the movies after add/remove movie from/to favourite
     */
    public void refreshFavourite() {
        ((MainActivity) getActivity()).getOps().executeGetMoviesTask("favourite", 0, false);
    }

    /**
     * Clear the content of the interface
     */
    private void clearUI() {
        mMovieReviewsLL.removeAllViews();
        mMovieVideosLL.removeAllViews();
        mMovieCastLL.removeAllViews();
        overviewTV.setText("");
        posterIV.setImageBitmap(null);
        mCollapsingToolbar.setTitle("");
    }

}
