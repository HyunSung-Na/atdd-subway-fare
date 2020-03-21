package atdd.path.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@ToString(exclude = {"line", "sourceStation", "targetStation"})
@NoArgsConstructor
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "source_station_id")
    private Station sourceStation;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "target_station_id")
    private Station targetStation;

    private int distance;

    private int elapsedTime;

    public Edge(Long id, Station sourceStation, Station targetStation, int distance) {
        this.id = id;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.distance = distance;
    }

    public Edge(Long id, Line line, Station sourceStation, Station targetStation, int distance, int elapsedTime) {
        this.id = id;
        this.line = line;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.distance = distance;
        this.elapsedTime = elapsedTime;
    }

    @Builder
    public Edge(Long id, Long lineId, Long sourceStationId, Long targetStationId, int distance, int elapsedTime) {
        this.id = id;
        this.line = new Line(lineId, null);
        this.sourceStation = new Station(sourceStationId, null);
        this.targetStation = new Station(targetStationId, null);
        this.distance = distance;
        this.elapsedTime = elapsedTime;
    }

    public static Edge of(Station sourceStation, Station targetStation, int distance) {
        return new Edge(null, sourceStation, targetStation, distance);
    }

    public boolean hasStation(Station station) {
        return sourceStation.equals(station) || targetStation.equals(station);
    }

    public void updateLine(Line line) {
        this.line = line;

    }

    public boolean isThisStation(Long stationId, boolean isUp) {
        if (isUp && this.sourceStation.getId().equals(stationId)) {
            return true;
        }

        if (!isUp && this.targetStation.getId().equals(stationId)) {
            return true;
        }

        return false;
    }
}
