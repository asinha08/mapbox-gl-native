#include <unaccent.hpp>
#include <string>
#include <src/java/lang.hpp>
#include "attach_env.hpp"
#include "text/collator_jni.hpp"

namespace mbgl {
namespace platform {

std::string unaccent(const std::string& str) {
    android::UniqueEnv env = android::AttachEnv();
    jni::String input = jni::Make<jni::String>(*env, str);
    jni::String unaccented = android::StringUtils::unaccent(*env, input);
    jni::DeleteLocalRef(*env, input);
    std::string output = jni::Make<std::string>(*env, unaccented);
    jni::DeleteLocalRef(*env, unaccented);
    return output;
}

} // namespace platform
} // namespace mbgl
