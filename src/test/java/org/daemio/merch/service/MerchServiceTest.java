package org.daemio.merch.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.daemio.merch.dto.MerchPage;
import org.daemio.merch.error.MerchNotFoundException;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.Merch;
import org.daemio.merch.repository.MerchRepository;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Merch service tests")
public class MerchServiceTest {

  @Mock private MerchRepository merchRepository;
  @Spy private MerchMapper merchMapper = Mappers.getMapper(MerchMapper.class);
  @Mock private Page<Merch> page;

  @InjectMocks private MerchService service;

  private MerchMapper mapper = Mappers.getMapper(MerchMapper.class);

  @DisplayName("when calling for a list of merch, then should return a list")
  @Test
  public void whenGettingList_thenReturnList() {
    List<Merch> expectedResult = Arrays.asList(new Merch());
    when(merchRepository.findAll()).thenReturn(expectedResult);

    List<Merch> actualResult = service.getMerchList();

    assertNotNull(actualResult, "Merch list is null");
    assertNotEquals(0, actualResult.size(), "Merch list is empty");
    assertArrayEquals(
        actualResult.toArray(), expectedResult.toArray(), "Merch list is not what was expected");
  }

  @Test
  public void whenGettingPagedList_theReturnList() {
    var merch = new Merch();
    merch.setCreatedTime(Instant.now());
    merch.setModifiedTime(Instant.now());

    var expectedResult = new MerchPage();
    expectedResult.setMerch(List.of(mapper.entityToModel(merch)));

    // Mocking here needs to change
    when(page.getContent()).thenReturn(List.of(merch));
    when(merchRepository.findAll(any(PageRequest.class))).thenReturn(page);

    var actualResult = service.getMerchPage(PageRequest.of(0, 1));

    assertNotNull(actualResult, "Merch list is null");
    assertNotEquals(0, actualResult.getMerch().size(), "Merch list is empty");
    assertArrayEquals(
        actualResult.getMerch().toArray(),
        expectedResult.getMerch().toArray(),
        "Merch list is not what was expected");
  }

  @DisplayName(
      "given some merch id and a merch item exists with that id, "
          + "when calling for merch with the given id, then return that specific merch item")
  @Test
  public void whenGettingSpecificMerch_thenReturnMerchItem() {
    Merch expectedResult = new Merch();
    expectedResult.setId(UUID.randomUUID());
    expectedResult.setCreatedTime(Instant.now());
    expectedResult.setModifiedTime(Instant.now());

    when(merchRepository.findById(expectedResult.getId())).thenReturn(Optional.of(expectedResult));

    var actualResult = service.getMerch(expectedResult.getId().toString());

    assertNotNull(actualResult, "Merch item is null");
    assertEquals(
        expectedResult.getId().toString(),
        actualResult.getMerchId(),
        "Ids of the merch do not match");
  }

  @DisplayName(
      "given some merch id and the merch item does not exist, when calling for "
          + "the merch item then throw the merch not found exception")
  @Test
  public void givenMerchNotThere_whenGettingMerchItem_thenThrowNotFoundException() {
    when(merchRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

    assertThrows(
        MerchNotFoundException.class,
        () -> service.getMerch(UUID.randomUUID().toString()),
        "Exception not thrown when no merch");
  }
}
