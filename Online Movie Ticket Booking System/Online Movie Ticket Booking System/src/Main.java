import java.util.*;

record Movie(String title, String genre) {
}

class Showtime {
    private final Movie movie;
    private final Date time;
    private final List<Seat> availableSeats;

    public Showtime(Movie movie, Date time, int totalSeats) {
        this.movie = movie;
        this.time = time;
        this.availableSeats = new ArrayList<>();
        initializeSeats(totalSeats);
    }

    private void initializeSeats(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            availableSeats.add(new Seat(i));
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public Date getTime() {
        return time;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }
}

class Seat {
    private final int seatNumber;
    private boolean isBooked;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookSeat() {
        isBooked = true;
    }

    public void cancelBooking() {
        isBooked = false;
    }
}

class Booking {
    private final Showtime showtime;
    private final List<Seat> bookedSeats;

    public Booking(Showtime showtime) {
        this.showtime = showtime;
        this.bookedSeats = new ArrayList<>();
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }

    public void bookSeat(Seat seat) {
        if (!seat.isBooked()) {
            seat.bookSeat();
            bookedSeats.add(seat);
            System.out.println("Seat " + seat.getSeatNumber() + " booked successfully.");
        } else {
            System.out.println("Seat " + seat.getSeatNumber() + " is already booked.");
        }
    }

    public void cancelBooking(Seat seat) {
        if (bookedSeats.contains(seat)) {
            seat.cancelBooking();
            bookedSeats.remove(seat);
            System.out.println("Booking for Seat " + seat.getSeatNumber() + " cancelled successfully.");
        } else {
            System.out.println("Seat " + seat.getSeatNumber() + " is not booked.");
        }
    }
}

class BookingSystem {
    private final List<Movie> movies;
    private final List<Showtime> showtimes;
    private final List<Booking> bookings;

    public BookingSystem() {
        this.movies = new ArrayList<>();
        this.showtimes = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addMovie(String title, String genre) {
        movies.add(new Movie(title, genre));
    }

    public void addShowtime(String movieTitle, Date time, int totalSeats) {
        Movie movie = findMovie(movieTitle);
        if (movie != null) {
            showtimes.add(new Showtime(movie, time, totalSeats));
        } else {
            System.out.println("Movie not found!");
        }
    }

    public void displayMovies() {
        System.out.println("Available Movies:");
        for (Movie movie : movies) {
            System.out.println("Title: " + movie.title() + ", Genre: " + movie.genre());
        }
    }

    public void displayShowtimes(String movieTitle) {
        Movie movie = findMovie(movieTitle);
        if (movie != null) {
            System.out.println("Showtimes for Movie: " + movie.title());
            for (Showtime showtime : showtimes) {
                if (showtime.getMovie().equals(movie)) {
                    System.out.println("Time: " + showtime.getTime() + ", Available Seats: " + showtime.getAvailableSeats().size());
                }
            }
        } else {
            System.out.println("Movie not found!");
        }
    }

    public void bookTicket(String movieTitle, Date time, int seatNumber) {
        Showtime showtime = findShowtime(movieTitle, time);
        if (showtime != null) {
            Seat seat = findSeat(showtime, seatNumber);
            if (seat != null) {
                Booking booking = new Booking(showtime);
                booking.bookSeat(seat);
                bookings.add(booking);
            } else {
                System.out.println("Seat not found!");
            }
        } else {
            System.out.println("Showtime not found!");
        }
    }

    public void cancelTicket(String movieTitle, Date time, int seatNumber) {
        Showtime showtime = findShowtime(movieTitle, time);
        if (showtime != null) {
            Seat seat = findSeat(showtime, seatNumber);
            if (seat != null) {
                Booking booking = findBooking(showtime, seat);
                if (booking != null) {
                    booking.cancelBooking(seat);
                } else {
                    System.out.println("Booking not found!");
                }
            } else {
                System.out.println("Seat not found!");
            }
        } else {
            System.out.println("Showtime not found!");
        }
    }

    private Movie findMovie(String title) {
        for (Movie movie : movies) {
            if (movie.title().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    private Showtime findShowtime(String movieTitle, Date time) {
        Movie movie = findMovie(movieTitle);
        for (Showtime showtime : showtimes) {
            if (showtime.getMovie().equals(movie) && showtime.getTime().equals(time)) {
                return showtime;
            }
        }
        return null;
    }

    private Seat findSeat(Showtime showtime, int seatNumber) {
        for (Seat seat : showtime.getAvailableSeats()) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }

    private Booking findBooking(Showtime showtime, Seat seat) {
        for (Booking booking : bookings) {
            if (booking.getShowtime().equals(showtime) && booking.getBookedSeats().contains(seat)) {
                return booking;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("1. Add Movie");
            System.out.println("2. Add Showtime");
            System.out.println("3. Display Movies");
            System.out.println("4. Display Showtimes");
            System.out.println("5. Book Ticket");
            System.out.println("6. Cancel Ticket");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Movie Title: ");
                    String movieTitle = scanner.next();
                    System.out.print("Enter Movie Genre: ");
                    String movieGenre = scanner.next();
                    bookingSystem.addMovie(movieTitle, movieGenre);
                    System.out.println("Movie added successfully!");
                    break;
                case 2:
                    System.out.print("Enter Movie Title: ");
                    String showtimeMovieTitle = scanner.next();
                    System.out.print("Enter Showtime (in milliseconds): ");
                    long showtimeMillis = scanner.nextLong();
                    Date showtimeDate = new Date(showtimeMillis);
                    System.out.print("Enter Total Seats: ");
                    int totalSeats = scanner.nextInt();
                    bookingSystem.addShowtime(showtimeMovieTitle, showtimeDate, totalSeats);
                    System.out.println("Showtime added successfully!");
                    break;
                case 3:
                    bookingSystem.displayMovies();
                    break;
                case 4:
                    System.out.print("Enter Movie Title: ");
                    String displayShowtimesMovieTitle = scanner.next();
                    bookingSystem.displayShowtimes(displayShowtimesMovieTitle);
                    break;
                case 5:
                    System.out.print("Enter Movie Title: ");
                    String bookTicketMovieTitle = scanner.next();
                    System.out.print("Enter Showtime (in milliseconds): ");
                    long bookTicketMillis = scanner.nextLong();
                    Date bookTicketDate = new Date(bookTicketMillis);
                    System.out.print("Enter Seat Number: ");
                    int seatNumber = scanner.nextInt();
                    bookingSystem.bookTicket(bookTicketMovieTitle, bookTicketDate, seatNumber);
                    break;
                case 6:
                    System.out.print("Enter Movie Title: ");
                    String cancelTicketMovieTitle = scanner.next();
                    System.out.print("Enter Showtime (in milliseconds): ");
                    long cancelTicketMillis = scanner.nextLong();
                    Date cancelTicketDate = new Date(cancelTicketMillis);
                    System.out.print("Enter Seat Number: ");
                    int cancelSeatNumber = scanner.nextInt();
                    bookingSystem.cancelTicket(cancelTicketMovieTitle, cancelTicketDate, cancelSeatNumber);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }
}
