package com.yogesh.controller;

import com.yogesh.dto.ApiResponse;
import com.yogesh.dto.ShowDto;
import com.yogesh.service.ShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ShowDto.ShowResponse>>> getAllShows() throws Exception {
        List<ShowDto.ShowResponse> shows = showService.getAllShows();
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Shows retrieved successfully", shows));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShowDto.ShowResponse>> getShowById(@PathVariable Long id) throws Exception {
        ShowDto.ShowResponse show = showService.getShowById(id);
        if (show == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "Show not found", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Show retrieved successfully", show));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addShow(@RequestBody ShowDto.ShowRequest request) throws Exception {
        showService.addShow(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Show added successfully", "Show created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateShow(@PathVariable Long id, @RequestBody ShowDto.ShowRequest request) throws Exception {
        showService.updateShow(id, request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Show updated successfully", "Show updated"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteShow(@PathVariable Long id) throws Exception {
        showService.deleteShow(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Show deleted successfully", "Show deleted"));
    }
}
