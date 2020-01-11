package zju.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import zju.se.modelandrepository.Record;
import zju.se.modelandrepository.RecordRepository;
import zju.se.modelandrepository.RecordResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
@RequestMapping(value = "/api")
public class RecordController {
    @Autowired
    private RecordRepository recordRepository;

    @RequestMapping(value = "/record", method = RequestMethod.POST)
    public List <RecordResponseBody> getRecord(
            @RequestBody Map<String, String> httpMessageBody
            )
    {
        List <RecordResponseBody> records = recordRepository.findAllByFundId(httpMessageBody.get("userinfo"))
                .stream()
                .map(r -> new RecordResponseBody(r))
                .collect(Collectors.toList());
        System.out.println(httpMessageBody.get("userinfo"));
        System.out.println(records);
        return records;
    }
}
