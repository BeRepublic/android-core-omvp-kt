package com.omvp.app.injector.module

import android.text.TextUtils

import com.omvp.commons.LocalDateUtils

import org.modelmapper.AbstractConverter
import org.modelmapper.ModelMapper
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

import java.math.BigDecimal

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import timber.log.Timber

@Module
object MapperModule {

    @JvmStatic
    @Provides
    @Singleton
    internal fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()

        addLocalDateConverter(modelMapper)
        addLocalDateTimeConverter(modelMapper)
        addBigDecimalConverter(modelMapper)

        return modelMapper
    }

    @JvmStatic
    private fun addLocalDateConverter(modelMapper: ModelMapper) {
        modelMapper.createTypeMap(Long::class.java, LocalDate::class.java)
        modelMapper.addConverter(object : AbstractConverter<Long, LocalDate>() {
            override fun convert(source: Long?): LocalDate? {
                return if (source == null) null else LocalDateUtils.toLocalDate(source * 1000)
            }
        })
        modelMapper.createTypeMap(LocalDate::class.java, Long::class.java)
        modelMapper.addConverter(object : AbstractConverter<LocalDate, Long>() {
            override fun convert(source: LocalDate?): Long? {
                return if (source == null) null else LocalDateUtils.toMiliseconds(source) / 1000
            }
        })
        modelMapper.createTypeMap(LocalDate::class.java, String::class.java)
        modelMapper.addConverter(object : AbstractConverter<LocalDate, String>() {
            override fun convert(source: LocalDate?): String? {
                return source?.format(DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault()))
            }
        })
        modelMapper.createTypeMap(String::class.java, LocalDate::class.java)
        modelMapper.addConverter(object : AbstractConverter<String, LocalDate>() {
            override fun convert(source: String?): LocalDate? {
                return if (source == null || TextUtils.isEmpty(source)) null else LocalDate.parse(source, DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault()))
            }
        })
    }

    @JvmStatic
    private fun addLocalDateTimeConverter(modelMapper: ModelMapper) {
        modelMapper.createTypeMap(Long::class.java, LocalDateTime::class.java)
        modelMapper.addConverter(object : AbstractConverter<Long, LocalDateTime>() {
            override fun convert(source: Long?): LocalDateTime? {
                return if (source == null) null else LocalDateTime.ofInstant(Instant.ofEpochMilli(source * 1000), ZoneId.systemDefault())
            }
        })
        modelMapper.createTypeMap(LocalDateTime::class.java, Long::class.java)
        modelMapper.addConverter(object : AbstractConverter<LocalDateTime, Long>() {
            override fun convert(source: LocalDateTime?): Long? {
                return if (source == null) null else source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000
            }
        })
        modelMapper.createTypeMap(LocalDateTime::class.java, String::class.java)
        modelMapper.addConverter(object : AbstractConverter<LocalDateTime, String>() {
            override fun convert(source: LocalDateTime?): String? {
                return source?.format(DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault()))
            }
        })
        modelMapper.createTypeMap(String::class.java, LocalDateTime::class.java)
        modelMapper.addConverter(object : AbstractConverter<String, LocalDateTime>() {
            override fun convert(source: String?): LocalDateTime? {
                return if (source == null || TextUtils.isEmpty(source)) null else LocalDateTime.parse(source, DateTimeFormatter.ISO_DATE.withZone(ZoneId.systemDefault()))
            }
        })
    }

    @JvmStatic
    private fun addBigDecimalConverter(modelMapper: ModelMapper) {
        modelMapper.createTypeMap(String::class.java, BigDecimal::class.java)
        modelMapper.addConverter(object : AbstractConverter<String, BigDecimal>() {
            override fun convert(source: String): BigDecimal? {
                var bigDecimal: BigDecimal? = null
                try {
                    val value = Integer.parseInt(source)
                    bigDecimal = BigDecimal.valueOf((value.toFloat() / 100).toDouble())
                } catch (e: NumberFormatException) {
                    Timber.e(e.message, e)
                }

                return bigDecimal
            }
        })
        modelMapper.createTypeMap(BigDecimal::class.java, String::class.java)
        modelMapper.addConverter(object : AbstractConverter<BigDecimal, String>() {
            override fun convert(source: BigDecimal): String {
                return source.toString()
            }
        })
    }

}