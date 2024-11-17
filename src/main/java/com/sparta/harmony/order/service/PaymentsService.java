package com.sparta.harmony.order.service;

import com.sparta.harmony.order.dto.PaymentsDetailResponseDto;
import com.sparta.harmony.order.dto.PaymentsResponseDto;
import com.sparta.harmony.order.dto.PaymentsResponsePageDto;
import com.sparta.harmony.order.entity.Payments;
import com.sparta.harmony.order.repository.PaymentsRepository;
import com.sparta.harmony.user.entity.Role;
import com.sparta.harmony.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentsService {

    private final PaymentsRepository paymentsRepository;

    // 결제 내역 조회.  user 이상 사용 가능
    @Transactional(readOnly = true)
    public PaymentsResponsePageDto getPayments(User user, int page, int size
            , String sortBy, boolean isAsc) {
        // 페이징 처리
        Pageable pageable = getPageable(page, size, sortBy, isAsc);
        Page<Payments> paymentsList;

        // 권한에 따른 조회. User, Owner의 경우 개인 결제 내역, manager 이상의 경우 모든 결제 내역
        Role userRoleEnum = user.getRole();

        if (userRoleEnum == Role.USER || userRoleEnum == Role.OWNER) {
            paymentsList = paymentsRepository.findAllByUserAndDeletedByFalse(user, pageable);
        } else {
            paymentsList = paymentsRepository.findAllByDeletedFalse(pageable);
        }

        Page<PaymentsResponseDto> paymentsResponseDto = paymentsList.map(PaymentsResponseDto::new);

        return new PaymentsResponsePageDto(
                paymentsResponseDto.getNumber() + 1, // 페이지 1부터 시작하도록
                paymentsResponseDto.getTotalPages(),
                paymentsResponseDto.getTotalElements(),
                paymentsResponseDto.getSize(),
                paymentsResponseDto.getContent()
        );
    }

    // 특정 가게의 결제 내역 조회. OWNER 이상 권한 필요
    @Transactional(readOnly = true)
    public PaymentsResponsePageDto getPaymentsByStoreId(UUID storeId, int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Pageable pageable = getPageable(page, size, sortBy, isAsc);
        Page<Payments> paymentsList;
        paymentsList = paymentsRepository.findPaymentsByStoreIdAndDeletedFalse(storeId, pageable);

        Page<PaymentsResponseDto> paymentsResponseDto = paymentsList.map(PaymentsResponseDto::new);

        return new PaymentsResponsePageDto(
                paymentsResponseDto.getNumber() + 1, // 페이지 1부터 시작하도록
                paymentsResponseDto.getTotalPages(),
                paymentsResponseDto.getTotalElements(),
                paymentsResponseDto.getSize(),
                paymentsResponseDto.getContent()
        );
    }

    // 결재 ID를 이용한 결재 상세 조회. user, owner의 경우 자기 자신의 결재정보만 조회
    public PaymentsDetailResponseDto getPaymentsByPaymentsId(UUID paymentsId, User user) {
        Role userRoleEnum = user.getRole();
        Payments payments;

        if (userRoleEnum == Role.USER || userRoleEnum == Role.OWNER) {
            payments = paymentsRepository.findByPaymentsIdAndUserAndDeletedFalse(paymentsId, user).orElseThrow(
                    () -> new IllegalArgumentException("고객님의 결재 내역이 없습니다."));
        } else {
            payments = paymentsRepository.findByPaymentsIdAndDeletedFalse(paymentsId).orElseThrow(
                    () -> new IllegalArgumentException("해당 결재 내역이 없습니다."));
        }

        return new PaymentsDetailResponseDto(payments);
    }

    private Pageable getPageable(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        return PageRequest.of(page, size, sort);
    }
}
