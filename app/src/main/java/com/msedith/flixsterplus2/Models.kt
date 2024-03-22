package com.msedith.flixsterplus2

data class PeopleResponse(
    val results: List<Person>
)

data class Person(
    val id: Int,
    val name: String,
    val profile_path: String,
    val popularity: Double,
    val known_for: List<Movie>
)

data class Movie(
    val id: Int,
    val title: String?,
    val originalTitle: String?,
    val overview: String?,
    val poster_path: String,
    val mediaType: String?,
    val releaseDate: String?,
    val voteAverage: Double
)