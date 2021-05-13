package org.ccci.gto.android.common.util;

import android.icu.util.ULocale;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IllformedLocaleException;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

public class LocaleUtils {
    // define a few fixed fallbacks
    static final Map<String, String> FALLBACKS = new HashMap<>();
    static {
        // macrolanguage fallbacks from https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry
        // Malagasy macrolanguage
        FALLBACKS.put("bhr", "mg");
        FALLBACKS.put("bjq", "mg");
        FALLBACKS.put("bmm", "mg");
        FALLBACKS.put("bzc", "mg");
        FALLBACKS.put("msh", "mg");
        FALLBACKS.put("plt", "mg");
        FALLBACKS.put("skg", "mg");
        FALLBACKS.put("tdx", "mg");
        FALLBACKS.put("tkg", "mg");
        FALLBACKS.put("txy", "mg");
        FALLBACKS.put("xmv", "mg");
        FALLBACKS.put("xmw", "mg");

        // Malay macrolanguage
        FALLBACKS.put("mfa", "ms");
        FALLBACKS.put("pse", "ms");
        FALLBACKS.put("zlm", "ms");
    }

    private static final Compat COMPAT;
    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            COMPAT = new LollipopCompat();
        } else {
            COMPAT = new NougatCompat();
        }
    }

    // region Language fallback methods

    public static void addFallback(@NonNull final String locale, @NonNull final String fallback) {
        if (FALLBACKS.get(locale) != null) {
            throw new IllegalStateException(locale + " already has a fallback language defined");
        }
        FALLBACKS.put(locale, fallback);
    }

    @Nullable
    public static Locale getFallback(@NonNull final Locale locale) {
        return COMPAT.getFallback(locale);
    }

    @NonNull
    public static Locale[] getFallbacks(@NonNull final Locale locale) {
        return COMPAT.getFallbacks(locale);
    }

    @NonNull
    public static Locale[] getFallbacks(@NonNull final Locale... locales) {
        final LinkedHashSet<Locale> outputs = new LinkedHashSet<>();

        // generate fallbacks for all provided locales
        for (final Locale locale : locales) {
            Collections.addAll(outputs, getFallbacks(locale));
        }

        return outputs.toArray(new Locale[0]);
    }

    // endregion Language fallback methods

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    interface Compat {
        @Nullable
        Locale getFallback(@NonNull Locale locale);

        @NonNull
        Locale[] getFallbacks(@NonNull Locale locale);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    static class LollipopCompat implements Compat {
        @Nullable
        @Override
        public Locale getFallback(@NonNull final Locale locale) {
            final Locale.Builder builder = new Locale.Builder();
            populateLocaleBuilder(builder, locale);
            return getFallback(locale, builder);
        }

        @Nullable
        public Locale getFallback(@NonNull final Locale locale, @NonNull final Locale.Builder builder) {
            // check for a direct fallback
            final String fallback = FALLBACKS.get(locale.toLanguageTag());
            if (fallback != null) {
                final Locale fallbackLocale = Locale.forLanguageTag(fallback);
                populateLocaleBuilder(builder, fallbackLocale);
                return fallbackLocale;
            }

            // try generating a fallback by eliminating parts of a language tag
            if (!locale.getExtensionKeys().isEmpty()) {
                return builder.clearExtensions().build();
            }
            if (!TextUtils.isEmpty(locale.getVariant())) {
                return builder.setVariant(null).build();
            }
            if (!TextUtils.isEmpty(locale.getCountry())) {
                return builder.setRegion(null).build();
            }
            if (!TextUtils.isEmpty(locale.getScript())) {
                return builder.setScript(null).build();
            }

            // default to no fallback
            return null;
        }

        @NonNull
        @Override
        public Locale[] getFallbacks(@NonNull final Locale locale) {
            // add initial locale
            final LinkedHashSet<Locale> locales = new LinkedHashSet<>();
            locales.add(locale);

            // generate all fallback variants
            final Locale.Builder builder = new Locale.Builder();
            populateLocaleBuilder(builder, locale);
            for (Locale fallback = locale; fallback != null;) {
                fallback = getFallback(fallback, builder);
                if (fallback != null) {
                    locales.add(fallback);
                }
            }

            // return the locales as an array
            return locales.toArray(new Locale[locales.size()]);
        }

        private void populateLocaleBuilder(@NonNull final Locale.Builder builder, @NonNull final Locale locale) {
            // populate builder from provided locale
            try {
                builder.setLocale(locale);
            } catch (final IllformedLocaleException e) {
                /* HACK: There appears to be a bug on Huawei devices running Android 5.0-5.1.1 using Arabic locales.
                         Setting the locale on the Locale Builder throws an IllformedLocaleException for "Invalid
                         variant: LNum". To workaround this bug we manually set the locale components on the builder,
                         skipping any invalid components
                   see: https://gist.github.com/frett/034b8eba09cf815cbcd60f83b3f52eb4
                */
                builder.clear();
                try {
                    builder.setLanguage(locale.getLanguage());
                } catch (final IllformedLocaleException ignored) {
                }
                try {
                    builder.setScript(locale.getScript());
                } catch (final IllformedLocaleException ignored) {
                }
                try {
                    builder.setRegion(locale.getCountry());
                } catch (final IllformedLocaleException ignored) {
                }
                try {
                    builder.setVariant(locale.getVariant());
                } catch (final IllformedLocaleException ignored) {
                }
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @RequiresApi(Build.VERSION_CODES.N)
    static final class NougatCompat extends LollipopCompat {
        @Nullable
        @Override
        public Locale getFallback(@NonNull final Locale locale) {
            final ULocale fallback = getFallback(ULocale.forLocale(locale));
            return fallback != null ? fallback.toLocale() : null;
        }

        @NonNull
        @Override
        public Locale[] getFallbacks(@NonNull final Locale rawLocale) {
            final ArrayList<Locale> locales = new ArrayList<>();
            locales.add(rawLocale);

            // handle fallback behavior
            for (ULocale locale = ULocale.forLocale(rawLocale); locale != null && !locale.equals(ULocale.ROOT);) {
                locale = getFallback(locale);
                if (locale != null) {
                    locales.add(locale.toLocale());
                }
            }

            return locales.toArray(new Locale[0]);
        }

        @Nullable
        private ULocale getFallback(@NonNull final ULocale locale) {
            // check for a fixed fallback
            final String fixed = FALLBACKS.get(locale.toLanguageTag());
            if (fixed != null) {
                return ULocale.forLanguageTag(fixed);
            }

            // remove extensions as the fallback if any are defined
            if (!locale.getExtensionKeys().isEmpty()) {
                return new ULocale.Builder().setLocale(locale).clearExtensions().build();
            }

            // use normal fallback behavior
            final ULocale fallback = locale.getFallback();
            if (fallback != null && !ULocale.ROOT.equals(fallback)) {
                return fallback;
            }

            // default to no fallback
            return null;
        }
    }
}
