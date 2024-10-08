package dev.ikm.tinkar.starterdata;

import dev.ikm.tinkar.entity.*;
import dev.ikm.tinkar.terms.EntityProxy;
import org.eclipse.collections.api.list.ImmutableList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PatternUtility {

    private static final Logger LOG = LoggerFactory.getLogger(PatternUtility.class.getSimpleName());

    private final UUIDUtility uuidUtility;

    public PatternUtility(UUIDUtility uuidUtility) {
        this.uuidUtility = uuidUtility;
    }

    protected Entity<? extends EntityVersion> createPattern(EntityProxy.Pattern pattern,
                            EntityProxy.Concept meaningConcept,
                            EntityProxy.Concept purposeConcept,
                            Entity<? extends EntityVersion> authoringSTAMP,
                            ImmutableList<FieldDefinitionRecord> fieldDefinitions){
        LOG.info("Building " + pattern.description() + " Chronology");

        RecordListBuilder<PatternVersionRecord> versions = RecordListBuilder.make();
        PatternRecord patternRecord = PatternRecordBuilder.builder()
                .nid(pattern.nid())
                .leastSignificantBits(pattern.asUuidArray()[0].getLeastSignificantBits())
                .mostSignificantBits(pattern.asUuidArray()[0].getMostSignificantBits())
                .additionalUuidLongs(null)
                .versions(versions.toImmutable())
                .build();

        LOG.info("Building " + pattern.description() + " Version");
        versions.add(PatternVersionRecordBuilder.builder()
                .chronology(patternRecord)
                .stampNid(authoringSTAMP.nid())
                .semanticMeaningNid(meaningConcept.nid())
                .semanticPurposeNid(purposeConcept.nid())
                .fieldDefinitions(fieldDefinitions)
                .build());

        return PatternRecordBuilder.builder(patternRecord).versions(versions.toImmutable()).build();
    }
}
