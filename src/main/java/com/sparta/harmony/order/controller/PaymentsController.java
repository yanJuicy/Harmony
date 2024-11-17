package com.sparta.harmony.order.controller;

import com.sparta.harmony.common.dto.ApiPageResponseDto;
import com.sparta.harmony.common.dto.ApiResponseDto;
import com.sparta.harmony.common.handler.success.SuccessResponseHandler;
import com.sparta.harmony.order.dto.PaymentsDetailResponseDto;
import com.sparta.harmony.order.dto.PaymentsResponseDto;
import com.sparta.harmony.order.service.PaymentsService;
import com.sparta.harmony.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PaymentsController {

    private final PaymentsService paymentsService;

    // user 이상 사용 가능
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @GetMapping("/payments")
    public ResponseEntity<ApiPageResponseDto<PaymentsResponseDto>> getPayments(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "is_asc", defaultValue = "false") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<PaymentsResponseDto> paymentsResponseDto = paymentsService.getPayments(userDetails.getUser(), page - 1, size, sortBy, isAsc);

        return new SuccessResponseHandler().handlePageSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                paymentsResponseDto
        );
    }

    // owner 이상만 사용 가능
    @PreAuthorize("hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @GetMapping("/payments/store/{storeId}")
    public ResponseEntity<ApiPageResponseDto<PaymentsResponseDto>> getPaymentsByStoreId(
            @PathVariable UUID storeId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort_by", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "is_asc", defaultValue = "false") boolean isAsc
    ) {
        Page<PaymentsResponseDto> paymentsResponseDto = paymentsService.getPaymentsByStoreId(storeId, page - 1, size, sortBy, isAsc);

        return new SuccessResponseHandler().handlePageSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                paymentsResponseDto
        );
    }

    // user 이상 사용 가능
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_OWNER')  or hasAuthority('ROLE_MANAGER') or hasAuthority('ROLE_MASTER')")
    @GetMapping("/payments/{paymentsId}")
    public ResponseEntity<ApiResponseDto<PaymentsDetailResponseDto>> getPaymentsByPaymentsId(@PathVariable UUID paymentsId,
                                                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PaymentsDetailResponseDto paymentsDetailResponseDto = paymentsService.getPaymentsByPaymentsId(paymentsId, userDetails.getUser());

        return new SuccessResponseHandler().handleSuccess(
                HttpStatus.OK,
                "조회에 성공하였습니다.",
                paymentsDetailResponseDto
        );
    }
}
