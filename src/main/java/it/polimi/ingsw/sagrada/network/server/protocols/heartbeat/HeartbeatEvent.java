package it.polimi.ingsw.sagrada.network.server.protocols.heartbeat;


/**
 * The Class HeartbeatEvent.
 */
public class HeartbeatEvent  {

    /** The source. */
    private String source;
    
    /** The time elapsed previous beat. */
    private int timeElapsedPreviousBeat;
    
    /** The time stamp. */
    private long timeStamp;

    /**
     * Instantiates a new heartbeat event.
     *
     * @param source the source
     * @param timeElapsedPreviousBeat the time elapsed previous beat
     * @param timeStamp the time stamp
     */
    HeartbeatEvent(String source, int timeElapsedPreviousBeat, long timeStamp) {
        this.source = source;
        this.timeElapsedPreviousBeat = timeElapsedPreviousBeat;
        this.timeStamp = timeStamp;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets the time from previous beat.
     *
     * @return the time from previous beat
     */
    public int getTimeFromPreviousBeat() {
        return timeElapsedPreviousBeat;
    }

    /**
     * Gets the beat time stamp.
     *
     * @return the beat time stamp
     */
    public long getBeatTimeStamp() {
        return timeStamp;
    }
}
