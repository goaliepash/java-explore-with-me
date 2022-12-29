package ru.practicum.explorewithme.stat.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.stat.model.EndpointHit;
import ru.practicum.explorewithme.stat.model.ViewStats;
import ru.practicum.explorewithme.stat.model.dto.EndpointHitDto;
import ru.practicum.explorewithme.stat.model.mapper.EndpointHitMapper;
import ru.practicum.explorewithme.stat.repository.StatRepository;
import ru.practicum.explorewithme.stat.service.StatService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatServiceImpl implements StatService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final StatRepository statRepository;

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointHitMapper.fromEndpointHitDto(endpointHitDto);
        return EndpointHitMapper.toEndpointHitDto(statRepository.save(endpointHit));
    }

    @Override
    public List<ViewStats> get(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startDateTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endDateTime = LocalDateTime.parse(end, FORMATTER);
        List<ViewStats> viewStats = statRepository
                .findAll(startDateTime, endDateTime, uris)
                .stream()
                .map(endpointHit -> {
                    ViewStats viewStat = new ViewStats();
                    viewStat.setApp(endpointHit.getApp());
                    viewStat.setUri(endpointHit.getUri());
                    viewStat.setHits(statRepository.countByApp(endpointHit.getApp()));
                    return viewStat;
                })
                .collect(Collectors.toList());
        if (unique) {
            return viewStats.stream().distinct().collect(Collectors.toList());
        }
        return viewStats;
    }
}