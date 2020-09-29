package com.swvl.swvlchallenge.data.model;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public class DataItem {
    private MovieItem movieItem;
    private Header headerItem;

    public Movie movie;
    public String header;

    public static class MovieItem extends DataItem {

        public MovieItem(Movie movie) {
            this.movie = movie;
        }
    }

    public static class Header extends DataItem {

        public Header(String header) {
            this.header = header;
        }
    }

    public MovieItem getMovieItem() {
        return movieItem;
    }

    public void setMovieItem(MovieItem movieItem) {
        this.movieItem = movieItem;
    }

    public Header getHeaderItem() {
        return headerItem;
    }

    public void setHeaderItem(Header headerItem) {
        this.headerItem = headerItem;
    }
}
